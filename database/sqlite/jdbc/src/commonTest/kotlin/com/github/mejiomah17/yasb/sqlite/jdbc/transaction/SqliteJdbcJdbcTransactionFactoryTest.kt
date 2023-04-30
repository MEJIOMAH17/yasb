package com.github.mejiomah17.yasb.sqlite.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.JdbcTransactionFactoryTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

class SqliteJdbcJdbcTransactionFactoryTest : JdbcTransactionFactoryTest() {
    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeAll
        @JvmStatic
        fun init() {
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite::memory:"
                }
            )
        }

        @AfterAll
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun createTransactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }

    override fun JdbcTransactionFactory<*>.callTransaction() {
        this.readUncommitted {
        }
    }
}
