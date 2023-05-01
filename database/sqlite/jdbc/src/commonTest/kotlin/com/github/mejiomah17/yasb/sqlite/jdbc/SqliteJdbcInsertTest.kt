package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.InsertWithReturningTest
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.TextParameter
import org.junit.Before
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcInsertTest :
    InsertWithReturningTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest() {
    @Before
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("DELETE FROM test")
            }
        }
    }

    override fun columnA(): Column<SqliteJdbcTestTable, String, ResultSet, PreparedStatement> {
        return SqliteJdbcTestTable.a
    }

    override fun columnB(): Column<SqliteJdbcTestTable, String, ResultSet, PreparedStatement> {
        return SqliteJdbcTestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    override fun tableTest(): SqliteJdbcTestTable {
        return SqliteJdbcTestTable
    }

    override fun transactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }
}
