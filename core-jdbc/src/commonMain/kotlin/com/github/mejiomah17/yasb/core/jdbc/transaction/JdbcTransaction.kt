package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.InsertWithReturn
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.jdbc.JdbcRows
import com.github.mejiomah17.yasb.core.query.Query
import com.github.mejiomah17.yasb.core.query.ReturningQuery
import com.github.mejiomah17.yasb.core.transaction.Transaction
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransaction : Transaction<ResultSet, PreparedStatement> {
    val connection: Connection

    // TODO test
    fun commit() {
        connection.commit()
    }

    // TODO test
    fun rollback() {
        connection.rollback()
    }

    override fun SelectQuery<ResultSet, PreparedStatement>.execute(): List<Row> {
        return lazy().use {
            it.toList()
        }
    }

    override fun SelectQuery<ResultSet, PreparedStatement>.lazy(): Rows {
        return executeQuery(buildSelectQuery())
    }

    override fun Query<ResultSet, PreparedStatement>.execute() {
        prepareStatement(this).execute()
    }

    override fun <TABLE : Table<TABLE, ResultSet, PreparedStatement>> InsertWithReturn<TABLE, ResultSet, PreparedStatement>.lazy(): Rows {
        return executeQuery(this.buildInsertQuery())
    }

    override fun <TABLE : Table<TABLE, ResultSet, PreparedStatement>> InsertWithReturn<TABLE, ResultSet, PreparedStatement>.execute(): List<Row> {
        return lazy().use { it.toList() }
    }

    private fun executeQuery(query: ReturningQuery<ResultSet, PreparedStatement>): JdbcRows {
        val statement = prepareStatement(query)
        return JdbcRows(statement, query, statement.executeQuery())
    }

    private fun prepareStatement(query: Query<ResultSet, PreparedStatement>): PreparedStatement {
        val statement = connection.prepareStatement(query.sql())
        query.parameters().forEachIndexed { i, parameter ->
            parameter.applyToStatement(statement, i + 1)
        }
        return statement
    }
}
