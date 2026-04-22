package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.SelectTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresSelectTest :
    PostgresTest(),
    SelectTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead> {
    override fun initSqlScripts(): List<String> = emptyList()
}
