package com.github.mejiomah17.yasb.dsl.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.sqlite.parameter.TextParameter
import com.github.mejiomah17.yasb.dsl.FromTest
import com.github.mejiomah17.yasb.dsl.sqlite.transaction.SqliteTransactionFactory
import org.junit.jupiter.api.BeforeEach

class SqliteFromTest : FromTest<TestTable>, SqliteTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("DELETE from test")
                it.execute(
                    """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | )
                    """.trimMargin()
                )
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

    override fun transactionFactory(): SqliteTransactionFactory {
        return SqliteTransactionFactory(dataSource)
    }
}
