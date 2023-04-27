package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.dsl.ConditionContext
import com.github.mejiomah17.yasb.core.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.dsl.WhereClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class Where<DRIVER_DATA_SOURCE>(
    private val select: FromClauseAndSelectQuery<DRIVER_DATA_SOURCE>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE>
) : WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE> {
    override fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE> {
        val selectExpression = select.buildSelectQuery()
        val whereExpression = where.build()
        return QueryForExecute(
            sqlDefinition = "${selectExpression.sqlDefinition} WHERE ${whereExpression.sqlDefinition}",
            returnExpressions = selectExpression.returnExpressions,
            parameters = selectExpression.parameters + whereExpression.parameters
        )
    }
}

fun <DRIVER_DATA_SOURCE> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE>.where(expression: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE>): Where<DRIVER_DATA_SOURCE> {
    return Where(this, expression(ConditionContext))
}
