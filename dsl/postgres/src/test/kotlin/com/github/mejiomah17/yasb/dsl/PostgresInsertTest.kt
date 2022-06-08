package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.TextParameter
import com.github.mejiomah17.yasb.dsl.transaction.PostgresTransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import io.kotest.matchers.shouldBe
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PostgresInsertTest : InsertWithReturningTest<TestTable>, PostgresTest() {
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
            }.execute()
            val row = select(columnA(), columnB(), TestTable.c, TestTable.d)
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
            row[TestTable.c] shouldBe randomUUID
            row[TestTable.d] shouldBe now
        }
    }

    override fun columnA(): Column<TestTable, String> {
        return TestTable.a
    }

    override fun columnB(): Column<TestTable, String> {
        return TestTable.b
    }

    override fun parameter(): Parameter<String> {
        return TextParameter("param")
    }

    override fun tableTest(): TestTable {
        return TestTable
    }

    override fun transactionFactory(): TransactionFactory {
        return PostgresTransactionFactory(dataSource)
    }
}