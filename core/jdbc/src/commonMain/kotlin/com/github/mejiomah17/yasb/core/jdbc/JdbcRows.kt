package com.github.mejiomah17.yasb.core.jdbc

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import java.sql.PreparedStatement
import java.sql.ResultSet

class JdbcRows(
    private val preparedStatement: PreparedStatement,
    private val queryForExecute: QueryForExecute,
    private val resultSet: ResultSet
) : Rows {
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
                return Row(
                    queryForExecute.returnExpressions.mapIndexed { index, expression ->
                        expression to expression.databaseType().jdbc().extractFromResultSet(resultSet, index + 1)
                    }.toMap()
                )
            }
        }
    }

    override fun close() {
        runCatching { preparedStatement.close() }
        runCatching { resultSet.close() }
    }
}
