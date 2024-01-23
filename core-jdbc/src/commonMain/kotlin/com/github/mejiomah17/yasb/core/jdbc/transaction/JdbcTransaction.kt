package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.jdbc.JdbcRows
import com.github.mejiomah17.yasb.core.query.Query
import com.github.mejiomah17.yasb.core.query.ReturningQuery
import com.github.mejiomah17.yasb.core.transaction.Transaction
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransaction : Transaction<ResultSet, PreparedStatement> {
    val connection: Connection

    fun commit() {
        connection.commit()
    }

    fun rollback() {
        connection.rollback()
    }

    override fun Query<ResultSet, PreparedStatement>.execute() {
        prepareStatement(this).execute()
    }

    override fun ReturningQuery<ResultSet, PreparedStatement>.lazy(): Rows {
        val statement = prepareStatement(this)
        return JdbcRows(statement, this, statement.executeQuery())
    }

    private fun prepareStatement(query: Query<ResultSet, PreparedStatement>): PreparedStatement {
        val statement = connection.prepareStatement(query.sql())
        query.parameters().forEachIndexed { i, parameter ->
            parameter.applyToStatement(statement, i + 1)
        }
        return statement
    }
}
