package com.github.mejiomah17.yasb.core.dsl.join

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.dsl.ConditionContext
import com.github.mejiomah17.yasb.core.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class TableJoin<S>(
    private val select: FromClauseAndSelectQuery<S>,
    private val with: SelectionSource<S>,
    private val joinType: JoinType,
    private val on: Expression<Boolean, S>
) : FromClauseAndSelectQuery<S> {
    override fun buildSelectQuery(): QueryForExecute<S> {
        val selectQuery = select.buildSelectQuery()
        val onQuery = on.build()
        return QueryForExecute(
            sqlDefinition = "${selectQuery.sqlDefinition} $joinType JOIN ${with.sqlDefinition} ON ${onQuery.sqlDefinition}",
            parameters = selectQuery.parameters + with.parameters + onQuery.parameters,
            returnExpressions = selectQuery.returnExpressions
        )
    }
}

fun <S> FromClauseAndSelectQuery<S>.innerJoin(
    with: SelectionSource<S>,
    on: ConditionContext.() -> Expression<Boolean, S>
): FromClauseAndSelectQuery<S> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.INNER,
        on = ConditionContext.on()
    )
}

fun <S> FromClauseAndSelectQuery<S>.leftJoin(
    with: SelectionSource<S>,
    on: ConditionContext.() -> Expression<Boolean, S>
): FromClauseAndSelectQuery<S> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.LEFT,
        on = ConditionContext.on()
    )
}

fun <S> FromClauseAndSelectQuery<S>.rightJoin(
    with: SelectionSource<S>,
    on: ConditionContext.() -> Expression<Boolean, S>
): FromClauseAndSelectQuery<S> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.RIGHT,
        on = ConditionContext.on()
    )
}

fun <S> FromClauseAndSelectQuery<S>.fullJoin(
    with: SelectionSource<S>,
    on: ConditionContext.() -> Expression<Boolean, S>
): FromClauseAndSelectQuery<S> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.FULL,
        on = ConditionContext.on()
    )
}
