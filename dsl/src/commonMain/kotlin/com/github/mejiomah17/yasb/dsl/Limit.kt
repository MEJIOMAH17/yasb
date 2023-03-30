@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class Limit internal constructor(
    private val selectQuery: SelectQuery,
    private val limit: Int
) : SelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val query = selectQuery.buildSelectQuery()
        return QueryForExecute(
            sqlDefinition = "${query.sqlDefinition} LIMIT $limit",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

context(DatabaseDialect, SupportsLimit)
fun FromClauseAndSelectQuery.limit(limit: Int): Limit {
    return Limit(
        this,
        limit
    )
}

context(DatabaseDialect, SupportsLimit)
fun WhereClauseAndSelectQuery.limit(limit: Int): Limit {
    return Limit(
        this,
        limit
    )
}
