package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.DeleteTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresDeleteTest :
    PostgresTest(),
    DeleteTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead> {
    override fun initSqlScripts(): List<String> =
        listOf(
            "TRUNCATE TABLE test",
            """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    |'2022-05-13 02:09:09.683194'::timestamp
                    | ),
                    | (
                    |'the aa',
                    |'the asd',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c222',
                    |'2022-05-13 02:09:09.683195'::timestamp
                    | )
            """.trimMargin(),
        )
}
