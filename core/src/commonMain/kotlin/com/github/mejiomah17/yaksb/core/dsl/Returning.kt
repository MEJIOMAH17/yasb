package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.SupportsInsertReturning
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.core.query.ReturningQuery

class Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val insert: InsertQuery<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
) : ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = expressions

    override fun sql(): String = insert.sql() + " RETURNING ${expressions.joinToString(", ") { it.sql() }}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> =
        insert.parameters() +
            expressions.flatMap {
                it.parameters()
            }
}

context(_: SupportsInsertReturning)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> InsertQuery<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.returning(
    expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
): Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = Returning(this, expressions)

context(_: SupportsInsertReturning)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> InsertQuery<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.returning(
    vararg expressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = Returning(this, expressions.toList())
