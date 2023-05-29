package com.github.mejiomah17.yasb.core.jdbc

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.query.OldReturningQuery
import com.github.mejiomah17.yasb.core.query.ReturningQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

// TODO INTERNAL
class JdbcRows(
    private val preparedStatement: PreparedStatement,
    private val returningQuery: OldReturningQuery<ResultSet, PreparedStatement>,
    private val resultSet: ResultSet
) : Rows {
    constructor(
        preparedStatement: PreparedStatement,
        returningQuery: ReturningQuery<ResultSet, PreparedStatement>,
        resultSet: ResultSet
    ) : this(
        preparedStatement,
        OldReturningQuery(returningQuery.sql(), returningQuery.parameters(), returningQuery.returnExpressions()),
        resultSet
    )

    override fun iterator(): Iterator<Row> {
        return object : Iterator<Row> {
            private var rowConsumed = true
            private var hasNext = false
            override fun hasNext(): Boolean {
                if (rowConsumed) {
                    hasNext = resultSet.next()
                    rowConsumed = false
                }
                return hasNext
            }

            override fun next(): Row {
                // call for side effect resultSet.next()
                hasNext()
                return Row(
                    returningQuery.returnExpressions.mapIndexed { index, expression ->
                        expression to expression.databaseType().extractFromSource(resultSet, index + 1)
                    }.toMap()
                ).also {
                    rowConsumed = true
                }
            }
        }
    }

    override fun close() {
        runCatching { preparedStatement.close() }
        runCatching { resultSet.close() }
    }
}
