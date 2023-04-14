package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.jdbc.JdbcRows
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.Insert
import com.github.mejiomah17.yasb.dsl.InsertWithReturn
import com.github.mejiomah17.yasb.dsl.SelectQuery
import com.github.mejiomah17.yasb.dsl.Update
import com.github.mejiomah17.yasb.dsl.jdbc
import java.sql.Connection

sealed interface Transaction {
    val connection: Connection

    // TODO test
    fun commit() {
        connection.commit()
    }

    // TODO test
    fun rollback() {
        connection.rollback()
    }

    fun SelectQuery.execute(): List<Row> {
        return lazy().use {
            it.toList()
        }
    }

    fun SelectQuery.lazy(): Rows {
        val queryForExecute = buildSelectQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        val resultSet = statement.executeQuery()
        return JdbcRows(statement, queryForExecute, resultSet)
    }

    fun <T : Table<T>> Insert<T>.execute() {
        val queryForExecute = buildInsertQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        statement.execute()
    }

    fun <T : Table<T>> Update<T>.execute() {
        val queryForExecute = buildUpdateQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        statement.execute()
    }

    fun <T : Table<T>> InsertWithReturn<T>.lazy(): Rows {
        val queryForExecute = buildInsertQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        val resultSet = statement.executeQuery()
        return JdbcRows(statement, queryForExecute, resultSet)
    }

    fun <T : Table<T>> InsertWithReturn<T>.execute(): List<Row> {
        return lazy().toList()
    }
}
