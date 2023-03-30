package com.github.mejiomah17.yasb.dsl.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yasb.dsl.LimitTest
import com.github.mejiomah17.yasb.dsl.sqlite.transaction.SqliteTransactionFactory
import org.junit.jupiter.api.BeforeEach

class SqliteLimitTest : LimitTest<TestTable, SqliteTransactionFactory, SqliteDatabaseDialect>, SqliteTest() {
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

    override fun transactionFactory(): SqliteTransactionFactory {
        return SqliteTransactionFactory(dataSource)
    }

    override fun columnA(): Column<TestTable, String> {
        return TestTable.a
    }

    override fun tableTest(): TestTable {
        return TestTable
    }
}
