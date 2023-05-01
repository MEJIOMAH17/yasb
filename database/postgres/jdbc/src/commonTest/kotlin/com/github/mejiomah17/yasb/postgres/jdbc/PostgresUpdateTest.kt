package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.UpdateTest
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import org.junit.Before
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresUpdateTest :
    UpdateTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    @Before
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
            }
        }
    }

    fun columnA(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.a
    }

    fun columnB(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    override fun tableTest(): PostgresJdbcTestTable {
        return PostgresJdbcTestTable
    }

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }
}
