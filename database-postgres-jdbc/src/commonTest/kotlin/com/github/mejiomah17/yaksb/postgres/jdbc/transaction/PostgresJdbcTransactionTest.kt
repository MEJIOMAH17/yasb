package com.github.mejiomah17.yaksb.postgres.jdbc.transaction

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.TransactionTest
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresContainer
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresContainer.Companion.LOGIN
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresContainer.Companion.PASSWORD
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresJdbcDatabaseDialect
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PostgresJdbcTransactionTest :
    TransactionTest<ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransactionRepeatableRead> {
    override fun <V> readCommitedTransaction(block: context(PostgresJdbcDatabaseDialect) JdbcTransactionRepeatableRead.() -> V): V =
        PostgresJdbcTransactionFactory(dataSource).repeatableRead {
            block()
        }

    companion object {
        lateinit var dataSource: HikariDataSource
        lateinit var container: PostgresContainer

        @BeforeClass
        @JvmStatic
        fun init() {
            container = PostgresContainer()
            container.start()
            dataSource =
                HikariDataSource(
                    HikariConfig().also {
                        it.jdbcUrl = container.jdbcUrl
                        it.username = LOGIN
                        it.password = PASSWORD
                    },
                )
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { container.close() }
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun exception(): Exception = SQLException()
}
