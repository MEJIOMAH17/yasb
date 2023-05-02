package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.dsl.LimitTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresLimitTest :
    LimitTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE test",
            """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    |'2022-05-13 02:09:09.683194'::timestamp
                    | ),
                    | (
                    |'the a',
                    |'the asd',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c222',
                    |'2022-05-13 02:09:09.683195'::timestamp
                    | )
            """.trimMargin()

        )
    }
}
