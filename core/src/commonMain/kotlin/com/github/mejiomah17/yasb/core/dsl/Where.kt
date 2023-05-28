package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.dsl.ConditionContext
import com.github.mejiomah17.yasb.core.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.dsl.WhereClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class Where<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val select: FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun buildSelectQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val selectExpression = select.buildSelectQuery()
        val whereExpression = where.build()
        return ReturningQuery(
            sql = "${selectExpression.sql} WHERE ${whereExpression.sql()}",
            returnExpressions = selectExpression.returnExpressions,
            parameters = selectExpression.parameters + whereExpression.parameters()
        )
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.where(
    expression: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): Where<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Where(this, expression(ConditionContext))
}
