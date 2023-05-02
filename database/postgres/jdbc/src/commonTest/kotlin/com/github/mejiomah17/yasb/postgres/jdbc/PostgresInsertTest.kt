package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.dsl.InsertWithReturningTest
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

class PostgresInsertTest :
    InsertWithReturningTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE test"
        )
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
                it[a] = "abc"
                it[b] = "bca"
                it[c] = randomUUID
                it[d] = now
                it[e] = 42.42
            }.execute()
            val row =
                select(
                    tableTest().a,
                    tableTest().b,
                    PostgresJdbcTestTable.c,
                    PostgresJdbcTestTable.d,
                    PostgresJdbcTestTable.e
                )
                    .from(tableTest())
                    .execute()
                    .single()
            row[tableTest().a] shouldBe "abc"
            row[tableTest().b] shouldBe "bca"
            row[PostgresJdbcTestTable.c] shouldBe randomUUID
            row[PostgresJdbcTestTable.d] shouldBe now
            row[PostgresJdbcTestTable.e] shouldBe 42.42
        }
    }
}
