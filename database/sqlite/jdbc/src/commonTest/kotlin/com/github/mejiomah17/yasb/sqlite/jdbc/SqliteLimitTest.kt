package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.LimitTest
import org.junit.Before
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteLimitTest :
    LimitTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction, SqliteJdbcTransactionFactory>,
    SqliteTest() {
    @Before
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

    override fun columnA(): Column<SqliteJdbcTestTable, String, ResultSet, PreparedStatement> {
        return SqliteJdbcTestTable.a
    }

    override fun tableTest(): SqliteJdbcTestTable {
        return SqliteJdbcTestTable
    }
}
