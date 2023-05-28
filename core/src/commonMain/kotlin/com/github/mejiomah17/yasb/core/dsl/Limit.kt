@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val selectQuery: SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val limit: Int
) : SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun buildSelectQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val query = selectQuery.buildSelectQuery()
        return ReturningQuery(
            sqlDefinition = "${query.sqlDefinition} LIMIT $limit",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

context(DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, SupportsLimit)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Limit(
        this,
        limit
    )
}

context(DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, SupportsLimit)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Limit(
        this,
        limit
    )
}
