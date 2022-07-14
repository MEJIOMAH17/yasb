package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.dsl.ConditionContext
import com.github.mejiomah17.yasb.dsl.FromClause
import com.github.mejiomah17.yasb.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.dsl.SelectQuery

class TableJoin(
    private val select: FromClauseAndSelectQuery,
    private val with: SelectionSource,
    private val joinType: JoinType,
    private val on: Expression<Boolean>,
) : FromClauseAndSelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val selectQuery = select.buildSelectQuery()
        val onQuery = on.build()
        return QueryForExecute(
            sqlDefinition = "${selectQuery.sqlDefinition} $joinType JOIN ${with.sqlDefinition} ON ${onQuery.sqlDefinition}",
            parameters = selectQuery.parameters + with.parameters + onQuery.parameters,
            returnExpressions = selectQuery.returnExpressions
        )
    }
}

fun FromClauseAndSelectQuery.innerJoin(
    with: SelectionSource,
    on: ConditionContext.() -> Expression<Boolean>
): FromClauseAndSelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.INNER,
        on = ConditionContext.on()
    )
}

fun FromClauseAndSelectQuery.leftJoin(
    with: SelectionSource,
    on: ConditionContext.() -> Expression<Boolean>
): FromClauseAndSelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.LEFT,
        on = ConditionContext.on()
    )
}

fun FromClauseAndSelectQuery.rightJoin(
    with: SelectionSource,
    on: ConditionContext.() -> Expression<Boolean>
): FromClauseAndSelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.RIGHT,
        on = ConditionContext.on()
    )
}

fun FromClauseAndSelectQuery.fullJoin(
    with: SelectionSource,
    on: ConditionContext.() -> Expression<Boolean>
): FromClauseAndSelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.FULL,
        on = ConditionContext.on()
    )
}