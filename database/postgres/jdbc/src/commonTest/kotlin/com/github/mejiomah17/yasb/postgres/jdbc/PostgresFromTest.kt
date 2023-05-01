package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.FromTest
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

class PostgresFromTest :
    FromTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    @Before
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
                it.execute(
                    """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    |'2022-05-13 02:09:09.683194'::timestamp
                    | )
                    """.trimMargin()
                )
            }
        }
    }

    fun columnA(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.a
    }

    fun columnB(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    override fun tableTest(): PostgresJdbcTestTable {
        return PostgresJdbcTestTable
    }

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    @Test
    override fun `from_returns_columns`() {
        transactionFactory().readUncommitted {
            val row = select(columnA(), columnB(), PostgresJdbcTestTable.c, PostgresJdbcTestTable.d)
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "the a"
            row[columnB()] shouldBe "the b"
            row[PostgresJdbcTestTable.c].toString() shouldBe "3e2220cd-e6a5-4eae-a258-6ed41e91c221"
            row[PostgresJdbcTestTable.d] shouldBe Timestamp.valueOf("2022-05-13 02:09:09.683194")
        }
    }
}
