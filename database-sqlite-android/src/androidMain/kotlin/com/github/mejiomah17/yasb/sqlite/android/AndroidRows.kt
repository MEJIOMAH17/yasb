package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.query.OldReturningQuery
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class AndroidRows(
    val cursor: Cursor,
    private val returningQuery: OldReturningQuery<Cursor, (String) -> Unit>
) : Rows {
    constructor(
        cursor: Cursor,
        returningQuery: ReturningQuery<Cursor, (String) -> Unit>
    ) : this(
        cursor,
        OldReturningQuery(returningQuery.sql(), returningQuery.parameters(), returningQuery.returnExpressions())
    )

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
                    returningQuery.returnExpressions.mapIndexed { index, expression ->
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
