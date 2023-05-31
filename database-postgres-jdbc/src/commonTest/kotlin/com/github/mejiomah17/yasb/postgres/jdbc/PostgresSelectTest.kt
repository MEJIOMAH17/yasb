package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.dsl.SelectTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresSelectTest :
    SelectTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return emptyList()
    }
}
