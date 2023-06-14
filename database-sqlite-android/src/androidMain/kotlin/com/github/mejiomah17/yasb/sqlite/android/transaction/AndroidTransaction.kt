package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.query.Query
import com.github.mejiomah17.yasb.core.query.ReturningQuery
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.sqlite.android.AndroidRows
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.sqlite.SQLiteDatabase

interface AndroidTransaction : Transaction<Cursor, AndroidSqliteDriverStatement> {
    val database: SQLiteDatabase

    override fun Query<Cursor, AndroidSqliteDriverStatement>.execute() {
        val adapter = SqliteDatabaseAdapter(database)
        adapter.executeQuery(this.sql(), this.parameters()).use {
            it.moveToNext()
        }
    }

    override fun ReturningQuery<Cursor, AndroidSqliteDriverStatement>.lazy(): Rows {
        val adapter = SqliteDatabaseAdapter(database)
        return AndroidRows(adapter.executeQuery(this.sql(), this.parameters()), this)
    }
}
