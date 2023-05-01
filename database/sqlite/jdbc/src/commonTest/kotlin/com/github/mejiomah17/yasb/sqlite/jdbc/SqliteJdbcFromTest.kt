package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.FromTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcFromTest :
    FromTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test",
            """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | )
            """.trimMargin()
        )
    }

    fun columnA(): Column<SqliteJdbcTestTable, String, ResultSet, PreparedStatement> {
        return SqliteJdbcTestTable.a
    }

    fun columnB(): Column<SqliteJdbcTestTable, String, ResultSet, PreparedStatement> {
        return SqliteJdbcTestTable.b
    }
}
