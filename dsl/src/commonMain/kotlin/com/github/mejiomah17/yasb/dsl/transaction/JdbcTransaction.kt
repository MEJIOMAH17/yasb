package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.Insert
import com.github.mejiomah17.yasb.core.dsl.InsertWithReturn
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.dsl.Update
import com.github.mejiomah17.yasb.core.jdbc.JdbcRows
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.dsl.jdbc
import java.sql.Connection
import java.sql.ResultSet

interface JdbcTransaction {
    val connection: Connection

    // TODO test
    fun commit() {
        connection.commit()
    }

    // TODO test
    fun rollback() {
        connection.rollback()
    }

    fun SelectQuery<*>.execute(): List<Row> {
        return lazy().use {
            it.toList()
        }
    }

    fun SelectQuery<*>.lazy(): Rows {
        val queryForExecute = buildSelectQuery() as QueryForExecute<ResultSet>
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any, ResultSet>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        val resultSet = statement.executeQuery()
        return JdbcRows(statement, queryForExecute, resultSet)
    }

    fun <TABLE : Table<TABLE, *>> Insert<TABLE, *>.execute() {
        val queryForExecute = buildInsertQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any, ResultSet>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        statement.execute()
    }

    fun <TABLE : Table<TABLE, *>> Update<TABLE, *>.execute() {
        val queryForExecute = buildUpdateQuery()
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any, ResultSet>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        statement.execute()
    }

    fun <TABLE : Table<TABLE, *>> InsertWithReturn<TABLE, *>.lazy(): Rows {
        val queryForExecute = buildInsertQuery() as QueryForExecute<ResultSet>
        val statement = connection.prepareStatement(queryForExecute.sqlDefinition)
        queryForExecute.parameters.forEachIndexed { i, parameter ->
            parameter as Parameter<Any, ResultSet>
            parameter.databaseType.jdbc().applyParameterToStatement(parameter, statement, i + 1)
        }
        val resultSet = statement.executeQuery()
        return JdbcRows(statement, queryForExecute, resultSet)
    }

    fun <TABLE : Table<TABLE, *>> InsertWithReturn<TABLE, *>.execute(): List<Row> {
        return lazy().toList()
    }
}
