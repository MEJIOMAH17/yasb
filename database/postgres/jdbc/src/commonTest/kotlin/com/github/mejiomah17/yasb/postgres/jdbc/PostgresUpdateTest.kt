package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.UpdateTest
import com.github.mejiomah17.yasb.dsl.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import org.junit.jupiter.api.BeforeEach
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresUpdateTest :
    UpdateTest<TestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
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

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }
}
