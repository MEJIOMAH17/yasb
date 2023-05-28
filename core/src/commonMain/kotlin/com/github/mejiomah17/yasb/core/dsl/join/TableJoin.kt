@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl.join

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.SupportsFullJoin
import com.github.mejiomah17.yasb.core.SupportsRightJoin
import com.github.mejiomah17.yasb.core.dsl.ConditionContext
import com.github.mejiomah17.yasb.core.dsl.FromClauseAndSelectQuery
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class TableJoin<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val select: FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val joinType: JoinType,
    private val on: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun buildSelectQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val selectQuery = select.buildSelectQuery()
        val onQuery = on.build()
        return ReturningQuery(
            sqlDefinition = "${selectQuery.sqlDefinition} $joinType JOIN ${with.sqlDefinition} ON ${onQuery.sqlDefinition}",
            parameters = selectQuery.parameters + with.parameters + onQuery.parameters,
            returnExpressions = selectQuery.returnExpressions
        )
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.innerJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.INNER,
        on = ConditionContext.on()
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.leftJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.LEFT,
        on = ConditionContext.on()
    )
}

context(SupportsRightJoin)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.rightJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.RIGHT,
        on = ConditionContext.on()
    )
}

context(SupportsFullJoin)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.fullJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.FULL,
        on = ConditionContext.on()
    )
}
