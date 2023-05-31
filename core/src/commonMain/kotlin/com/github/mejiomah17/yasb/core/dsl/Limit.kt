@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val query: SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val limit: Int
) : SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return query.returnExpressions()
    }

    override fun sql(): String {
        return "${query.sql()} LIMIT $limit"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return query.parameters()
    }
}

context(DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, SupportsLimit)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Limit(
        this,
        limit
    )
}

context(DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, SupportsLimit)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Limit(
        this,
        limit
    )
}
