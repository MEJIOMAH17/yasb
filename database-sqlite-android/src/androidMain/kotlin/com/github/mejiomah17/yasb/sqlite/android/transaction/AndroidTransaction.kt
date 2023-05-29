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
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.ReturningQuery
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
        return executeQuery(buildInsertQuery())
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> Update<TABLE, Cursor, (String) -> Unit>.execute() {
        return executeQuery(buildUpdateQuery())
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> InsertWithReturn<TABLE, Cursor, (String) -> Unit>.lazy(): Rows {
        return executeQuery(buildInsertQuery())
    }

    override fun <TABLE : Table<TABLE, Cursor, (String) -> Unit>> InsertWithReturn<TABLE, Cursor, (String) -> Unit>.execute(): List<Row> {
        return lazy().use {
            it.toList()
        }
    }

    private fun executeQuery(query: ReturningQuery<Cursor, (String) -> Unit>): AndroidRows {
        val statement = database.rawQuery(query.sql, query.args())
        return AndroidRows(statement, query)
    }

    private fun executeQuery(query: QueryPart<Cursor, (String) -> Unit>) {
        database.execSQL(query.sql(), query.args())
    }

    private fun QueryPart<Cursor, (String) -> Unit>.args(): Array<String> {
        val params = parameters()
        return Array(params.size) {
            var value = ""
            params[it].applyToStatement({ value = it }, it)
            value
        }
    }
}
