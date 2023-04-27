package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.InsertWithReturningTest
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

class PostgresInsertTest : InsertWithReturningTest<TestTable, ResultSet, PostgresJdbcDatabaseDialect>, PostgresTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
            }
        }
    }

    @Test
    override fun select_values_after_insert() {
        val randomUUID = UUID.randomUUID()
        val now = Timestamp.from(Instant.now())
        // Postgres does not support nanoseconds
        // https://github.com/pgjdbc/pgjdbc/blob/master/pgjdbc/src/main/java/org/postgresql/jdbc/TimestampUtils.java#L699
        now.nanos = 0
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
                it[c] = randomUUID
                it[d] = now
                it[e] = 42.42
            }.execute()
            val row = select(columnA(), columnB(), TestTable.c, TestTable.d, TestTable.e)
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
            row[TestTable.c] shouldBe randomUUID
            row[TestTable.d] shouldBe now
            row[TestTable.e] shouldBe 42.42
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
}
