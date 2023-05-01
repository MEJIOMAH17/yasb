package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.Insert
import com.github.mejiomah17.yasb.core.dsl.InsertWithReturn
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.dsl.Update
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.sqlite.android.AndroidRows

interface AndroidTransaction : Transaction<Cursor, (String) -> Unit> {
    val database: SQLiteDatabase

    override fun SelectQuery<Cursor, (String) -> Unit>.execute(): List<Row> {
        return lazy().use {
            it.toList()
        }
    }

    override fun SelectQuery<Cursor, (String) -> Unit>.lazy(): Rows {
        return executeQuery(buildSelectQuery())
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> Insert<TABLE, Cursor, (String) -> Unit>.execute() {
        TODO()
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> Update<TABLE, Cursor, (String) -> Unit>.execute() {
        TODO()
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> InsertWithReturn<TABLE, Cursor, (String) -> Unit>.lazy(): Rows {
        TODO()
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> InsertWithReturn<TABLE, Cursor, (String) -> Unit>.execute(): List<Row> {
        TODO()
    }

    private fun executeQuery(query: QueryForExecute<Cursor, (String) -> Unit>): AndroidRows {
        val selectionArgs = Array(query.parameters.size) {
            var value = ""
            query.parameters[it].applyToStatement({ value = it }, it)
            value
        }
        val statement = database.rawQuery(query.sqlDefinition, selectionArgs)
        return AndroidRows(statement, query)
    }
}
