package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.TextParameter
import com.github.mejiomah17.yasb.dsl.transaction.PostgresTransactionFactory
import org.junit.jupiter.api.BeforeEach

class PostgresUpdateTest : UpdateTest<TestTable>, PostgresTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
            }
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

    override fun transactionFactory(): PostgresTransactionFactory {
        return PostgresTransactionFactory(dataSource)
    }
}