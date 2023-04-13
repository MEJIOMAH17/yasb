package com.github.mejiomah17.yasb.dsl.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.dsl.LimitTest
import com.github.mejiomah17.yasb.dsl.postgres.transaction.PostgresJdbcTransactionFactory
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import org.junit.jupiter.api.BeforeEach

class PostgresLimitTest : LimitTest<TestTable, PostgresJdbcTransactionFactory, PostgresJdbcDatabaseDialect>, PostgresTest() {
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
    }

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    override fun tableTest(): TestTable {
        return TestTable
    }

    override fun columnA(): Column<TestTable, String> {
        return TestTable.a
    }
}
