package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.dsl.ConditionContext
import com.github.mejiomah17.yasb.dsl.SelectQuery

class TableJoin(
    private val select: SelectQuery,
    private val with: SelectionSource,
    private val joinType: JoinType,
    private val on: Expression<Boolean>,
) : SelectQuery {
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

fun SelectQuery.innerJoin(with: SelectionSource, on: ConditionContext.() -> Expression<Boolean>): SelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.INNER,
        on = ConditionContext.on()
    )
}

fun SelectQuery.leftJoin(with: SelectionSource, on: ConditionContext.() -> Expression<Boolean>): SelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.LEFT,
        on = ConditionContext.on()
    )
}

fun SelectQuery.rightJoin(with: SelectionSource, on: ConditionContext.() -> Expression<Boolean>): SelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.RIGHT,
        on = ConditionContext.on()
    )
}

fun SelectQuery.fullJoin(with: SelectionSource, on: ConditionContext.() -> Expression<Boolean>): SelectQuery {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.FULL,
        on = ConditionContext.on()
    )
}