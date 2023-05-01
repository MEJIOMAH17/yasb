package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.LimitTest
import org.junit.Before
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresLimitTest :
    LimitTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction, PostgresJdbcTransactionFactory>,
    PostgresTest() {
    @Before
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

    override fun tableTest(): PostgresJdbcTestTable {
        return PostgresJdbcTestTable
    }

    override fun columnA(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.a
    }
}
