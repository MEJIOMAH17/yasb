package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.UpdateTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresUpdateTest :
    UpdateTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE test"
        )
    }
}
