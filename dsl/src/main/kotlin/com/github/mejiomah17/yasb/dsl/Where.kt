package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.dsl.ConditionContext
import com.github.mejiomah17.yasb.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.dsl.SelectQuery
import com.github.mejiomah17.yasb.dsl.WhereClauseAndSelectQuery

class Where(
    private val select: FromClauseAndSelectQuery,
    private val where: Expression<Boolean>
) : WhereClauseAndSelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val selectExpression = select.buildSelectQuery()
        val whereExpression = where.build()
        return QueryForExecute(
            sqlDefinition = "${selectExpression.sqlDefinition} WHERE ${whereExpression.sqlDefinition}",
            returnExpressions = selectExpression.returnExpressions,
            parameters = selectExpression.parameters + whereExpression.parameters
        )
    }
}

fun FromClauseAndSelectQuery.where(expression: ConditionContext.() -> Expression<Boolean>): Where {
    return Where(this, expression(ConditionContext))
}

