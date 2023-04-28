package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.dsl.LimitTest
import com.github.mejiomah17.yasb.dsl.transaction.JdbcTransaction
import org.junit.jupiter.api.BeforeEach
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteLimitTest :
    LimitTest<TestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction, SqliteJdbcTransactionFactory>,
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

    override fun columnA(): Column<TestTable, String, ResultSet, PreparedStatement> {
        return TestTable.a
    }

    override fun tableTest(): TestTable {
        return TestTable
    }
}
