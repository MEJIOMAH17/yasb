package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute
import java.io.Closeable
import java.sql.PreparedStatement
import java.sql.ResultSet

class Rows(
    private val preparedStatement: PreparedStatement,
    private val queryForExecute: QueryForExecute,
    private val resultSet: ResultSet,
) : Sequence<Row>, Closeable {
    override fun iterator(): Iterator<Row> {
        return object : Iterator<Row> {
            private var rowConsumed = true
            override fun hasNext(): Boolean {
                if (rowConsumed) {
                    rowConsumed = resultSet.next()
                }
                return rowConsumed
            }

            override fun next(): Row {
                return Row(queryForExecute.returnExpressions.mapIndexed { index, expression ->
                    expression to expression.databaseType().extractFromResultSet(resultSet, index + 1)
                }.toMap())
            }
        }
    }

    override fun close() {
        runCatching { preparedStatement.close() }
        runCatching { resultSet.close() }
    }
}