package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.SelectTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcSelectTest :
    SelectTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest() {
    override fun initSqlScripts(): List<String> {
        return emptyList()
    }
}
