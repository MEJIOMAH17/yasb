package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class AndroidRows(
    val cursor: Cursor,
    private val queryForExecute: QueryForExecute<Cursor, (String) -> Unit>
) : Rows {
    override fun iterator(): Iterator<Row> {
        return object : Iterator<Row> {
            private var rowConsumed = true
            private var hasNext = false
            override fun hasNext(): Boolean {
                if (rowConsumed) {
                    hasNext = cursor.moveToNext()
                    rowConsumed = false
                }
                return hasNext
            }

            override fun next(): Row {
                // call for side effect resultSet.next()
                hasNext()
                return Row(
                    queryForExecute.returnExpressions.mapIndexed { index, expression ->
                        expression to expression.databaseType().extractFromSource(cursor, index)
                    }.toMap()
                ).also {
                    rowConsumed = true
                }
            }
        }
    }

    override fun close() {
        cursor.close()
    }
}