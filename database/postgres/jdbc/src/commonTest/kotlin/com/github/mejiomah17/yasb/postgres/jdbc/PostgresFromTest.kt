package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.FromTest
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

class PostgresFromTest :
    FromTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE test",
            """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    |'2022-05-13 02:09:09.683194'::timestamp
                    | )
            """.trimMargin()

        )
    }

    @Test
    override fun `from_returns_columns`() {
        transactionFactory().readUncommitted {
            val row = select(tableTest().a, tableTest().b, PostgresJdbcTestTable.c, PostgresJdbcTestTable.d)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "the a"
            row[tableTest().b] shouldBe "the b"
            row[PostgresJdbcTestTable.c].toString() shouldBe "3e2220cd-e6a5-4eae-a258-6ed41e91c221"
            row[PostgresJdbcTestTable.d] shouldBe Timestamp.valueOf("2022-05-13 02:09:09.683194")
        }
    }
}
