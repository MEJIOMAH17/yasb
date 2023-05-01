package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.WhereTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcWhereTest :
    WhereTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest() {

    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test",
            """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | )
            """.trimMargin(),
            """ INSERT INTO test (a,b) values (
                    '42',
                    '42'
                    )
            """.trimIndent()
        )
    }
}
