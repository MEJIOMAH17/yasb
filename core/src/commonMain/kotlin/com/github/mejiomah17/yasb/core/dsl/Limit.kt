@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class Limit<DRIVER_DATA_SOURCE> internal constructor(
    private val selectQuery: SelectQuery<DRIVER_DATA_SOURCE>,
    private val limit: Int
) : SelectQuery<DRIVER_DATA_SOURCE> {
    override fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE> {
        val query = selectQuery.buildSelectQuery()
        return QueryForExecute(
            sqlDefinition = "${query.sqlDefinition} LIMIT $limit",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

context(DatabaseDialect<DRIVER_DATA_SOURCE>, SupportsLimit)
fun <DRIVER_DATA_SOURCE> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE> {
    return Limit(
        this,
        limit
    )
}

context(DatabaseDialect<DRIVER_DATA_SOURCE>, SupportsLimit)
fun <DRIVER_DATA_SOURCE> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE>.limit(limit: Int): Limit<DRIVER_DATA_SOURCE> {
    return Limit(
        this,
        limit
    )
}
