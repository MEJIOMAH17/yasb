@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl.join

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.SupportsFullJoin
import com.github.mejiomah17.yasb.core.SupportsRightJoin
import com.github.mejiomah17.yasb.core.dsl.ConditionContext
import com.github.mejiomah17.yasb.core.dsl.SelectFromQuery
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class TableJoin<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val select: SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val joinType: JoinType,
    private val on: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return select.returnExpressions()
    }

    override fun sql(): String {
        return "${select.sql()} $joinType JOIN ${with.sql()} ON ${on.sql()}"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return select.parameters() + with.parameters() + on.parameters()
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.innerJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.INNER,
        on = ConditionContext.on()
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.leftJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.LEFT,
        on = ConditionContext.on()
    )
}

context(SupportsRightJoin)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.rightJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.RIGHT,
        on = ConditionContext.on()
    )
}

context(SupportsFullJoin)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.fullJoin(
    with: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    on: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableJoin(
        select = this,
        with = with,
        joinType = JoinType.FULL,
        on = ConditionContext.on()
    )
}
