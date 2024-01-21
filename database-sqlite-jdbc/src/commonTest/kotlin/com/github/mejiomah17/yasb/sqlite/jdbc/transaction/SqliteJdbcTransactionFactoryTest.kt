package com.github.mejiomah17.yasb.sqlite.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactory
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactoryTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass

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

    override fun createTransactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }

    override fun JdbcTransactionFactory<*>.callTransaction(block: () -> Any): Any {
        return this.readUncommitted {
            block()
        }
    }
}
