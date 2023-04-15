package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.dsl.LimitTest
import org.junit.jupiter.api.BeforeEach
import java.sql.ResultSet

class SqliteLimitTest : LimitTest<TestTable, SqliteJdbcTransactionFactory, SqliteJdbcDatabaseDialect, ResultSet>,
    SqliteTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("DELETE FROM test")
                it.execute(
                    """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | ),
                    | (
                    |'the a',
                    |'the asd'
                    | )
                    """.trimMargin()
                )
            }
        }
    }

    override fun transactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }

    override fun columnA(): Column<TestTable, String, ResultSet> {
        return TestTable.a
    }

    override fun tableTest(): TestTable {
        return TestTable
    }
}
