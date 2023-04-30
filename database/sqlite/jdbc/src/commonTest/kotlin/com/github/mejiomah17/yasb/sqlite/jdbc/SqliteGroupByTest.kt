package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.GroupByTest
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.TextParameter
import org.junit.jupiter.api.BeforeEach
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteGroupByTest :
    GroupByTest<TestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
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

    override fun columnA(): Column<TestTable, String, ResultSet, PreparedStatement> {
        return TestTable.a
    }

    override fun columnB(): Column<TestTable, String, ResultSet, PreparedStatement> {
        return TestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    override fun tableTest(): TestTable {
        return TestTable
    }

    override fun transactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }
}
