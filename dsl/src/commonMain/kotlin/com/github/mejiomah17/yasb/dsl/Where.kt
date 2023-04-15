package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.dsl.ConditionContext
import com.github.mejiomah17.yasb.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.dsl.WhereClauseAndSelectQuery

class Where<S>(
    private val select: FromClauseAndSelectQuery<S>,
    private val where: Expression<Boolean, S>
) : WhereClauseAndSelectQuery<S> {
    override fun buildSelectQuery(): QueryForExecute<S> {
        val selectExpression = select.buildSelectQuery()
        val whereExpression = where.build()
        return QueryForExecute(
            sqlDefinition = "${selectExpression.sqlDefinition} WHERE ${whereExpression.sqlDefinition}",
            returnExpressions = selectExpression.returnExpressions,
            parameters = selectExpression.parameters + whereExpression.parameters
        )
    }
}

fun <S> FromClauseAndSelectQuery<S>.where(expression: ConditionContext.() -> Expression<Boolean, S>): Where<S> {
    return Where(this, expression(ConditionContext))
}
