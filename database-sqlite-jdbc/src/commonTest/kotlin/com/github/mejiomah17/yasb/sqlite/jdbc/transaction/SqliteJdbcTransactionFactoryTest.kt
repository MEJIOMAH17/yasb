package com.github.mejiomah17.yasb.sqlite.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactoryTest
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass
import java.sql.SQLException

class SqliteJdbcTransactionFactoryTest : JdbcTransactionFactoryTest() {
    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeClass
        @JvmStatic
        fun init() {
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite::memory:"
                }
            )
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun transactionFactory(): TransactionFactory<*, *, *, *, *, *, *> {
        return SqliteJdbcTransactionFactory(dataSource)
    }

    override fun exception(): Exception {
        return SQLException()
    }
}
