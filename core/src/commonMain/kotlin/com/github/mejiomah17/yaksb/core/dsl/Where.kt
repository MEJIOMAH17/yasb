package com.github.mejiomah17.yaksb.core

import com.github.mejiomah17.yaksb.core.dsl.ConditionContext
import com.github.mejiomah17.yaksb.core.dsl.DeleteFromQuery
import com.github.mejiomah17.yaksb.core.dsl.DeleteWhereQuery
import com.github.mejiomah17.yaksb.core.dsl.SelectFromQuery
import com.github.mejiomah17.yaksb.core.dsl.SelectWhereQuery
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

internal class DeleteWhere<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val delete: DeleteFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : DeleteWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun sql(): String = "${delete.sql()} WHERE ${where.sql()}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = delete.parameters() + where.parameters()
}

internal class SelectWhereWhere<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val query: SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : SelectWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = query.returnExpressions()

    override fun sql(): String = "${query.sql()} WHERE ${where.sql()}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = query.parameters() + where.parameters()
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.where(
    expression: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): SelectWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = SelectWhereWhere(this, expression(ConditionContext))

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> DeleteFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.where(
    expression: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): DeleteWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = DeleteWhere(this, expression(ConditionContext))
