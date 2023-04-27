@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class Limit<S> internal constructor(
    private val selectQuery: SelectQuery<S>,
    private val limit: Int
) : SelectQuery<S> {
    override fun buildSelectQuery(): QueryForExecute<S> {
        val query = selectQuery.buildSelectQuery()
        return QueryForExecute(
            sqlDefinition = "${query.sqlDefinition} LIMIT $limit",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

context(DatabaseDialect<S>, SupportsLimit)
fun <S> FromClauseAndSelectQuery<S>.limit(limit: Int): Limit<S> {
    return Limit(
        this,
        limit
    )
}

context(DatabaseDialect<S>, SupportsLimit)
fun <S> WhereClauseAndSelectQuery<S>.limit(limit: Int): Limit<S> {
    return Limit(
        this,
        limit
    )
}
