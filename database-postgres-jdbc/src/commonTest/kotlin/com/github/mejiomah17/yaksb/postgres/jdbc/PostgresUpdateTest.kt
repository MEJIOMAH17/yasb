package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.UpdateTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresUpdateTest :
    UpdateTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE test",
        )
    }
}
