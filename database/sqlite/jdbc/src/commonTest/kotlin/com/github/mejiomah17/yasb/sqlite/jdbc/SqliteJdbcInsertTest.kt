package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.InsertWithReturningTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcInsertTest :
    InsertWithReturningTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test"
        )
    }
}
