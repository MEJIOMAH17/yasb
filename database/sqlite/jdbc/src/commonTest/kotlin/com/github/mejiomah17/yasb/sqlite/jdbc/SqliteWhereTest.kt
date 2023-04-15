package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.WhereTest
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.TextParameter
import org.junit.jupiter.api.BeforeEach
import java.sql.ResultSet

class SqliteWhereTest : WhereTest<TestTable, ResultSet, SqliteJdbcDatabaseDialect>, SqliteTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("DELETE FROM test")
                it.execute(
                    """INSERT INTO test (a,b) values (
                    'the a',
                    'the b'
                     );
                    """.trimIndent()
                )
                it.execute(
                    """ INSERT INTO test (a,b) values (
                    '42',
                    '42'
                    )
                    """.trimIndent()
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

    override fun transactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }
}
