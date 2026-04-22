package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

interface OrderByQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

internal class OrderBy<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val query: SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val orderingExpressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
) : OrderByQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = query.returnExpressions()

    override fun sql(): String = "${query.sql()} ORDER BY ${orderingExpressions.joinToString(", ") { it.sql() }}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> =
        query.parameters() +
            orderingExpressions.flatMap {
                it.parameters()
            }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.orderBy(
    orderingExpressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
): OrderByQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = OrderBy(this, orderingExpressions)

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.orderBy(
    vararg orderingExpressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): OrderByQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = OrderBy(this, orderingExpressions.toList())

internal class AscExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val delegate: Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = delegate.databaseType()

    override fun sql(): String = delegate.sql() + " ASC"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = delegate.parameters()
}

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.asc():
    Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    AscExpression(this)

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.desc():
    Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    DescExpression(this)

internal class DescExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val delegate: Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = delegate.databaseType()

    override fun sql(): String = delegate.sql() + " DESC"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = delegate.parameters()
}
