package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.postgres.PostgresDatabaseDialect
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

context(PostgresDatabaseDialect)
fun FromClauseAndSelectQuery.limit(limit: Int): Limit {
    return Limit(
        this,
        limit
    )
}

context(PostgresDatabaseDialect)
fun WhereClauseAndSelectQuery.limit(limit: Int): Limit {
    return Limit(
        this,
        limit
    )
}
