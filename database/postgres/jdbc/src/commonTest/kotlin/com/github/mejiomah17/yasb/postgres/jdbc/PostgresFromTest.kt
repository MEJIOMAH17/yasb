package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.FromTest
import com.github.mejiomah17.yasb.dsl.from
import com.github.mejiomah17.yasb.dsl.select
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.ResultSet
import java.sql.Timestamp

class PostgresFromTest : FromTest<TestTable, ResultSet, PostgresJdbcDatabaseDialect>, PostgresTest() {
    @BeforeEach
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

    override fun columnA(): Column<TestTable, String, ResultSet> {
        return TestTable.a
    }

    override fun columnB(): Column<TestTable, String, ResultSet> {
        return TestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet> {
        return TextParameter("param")
    }

    override fun tableTest(): TestTable {
        return TestTable
    }

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    @Test
    override fun `from returns columns`() {
        transactionFactory().readUncommitted {
            val row = select(columnA(), columnB(), TestTable.c, TestTable.d)
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "the a"
            row[columnB()] shouldBe "the b"
            row[TestTable.c].toString() shouldBe "3e2220cd-e6a5-4eae-a258-6ed41e91c221"
            row[TestTable.d] shouldBe Timestamp.valueOf("2022-05-13 02:09:09.683194")
        }
    }
}
