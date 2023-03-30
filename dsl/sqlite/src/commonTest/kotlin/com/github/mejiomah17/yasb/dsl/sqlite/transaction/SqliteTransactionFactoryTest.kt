package com.github.mejiomah17.yasb.dsl.sqlite.transaction

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactoryTest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

class SqliteTransactionFactoryTest : TransactionFactoryTest() {
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

    override fun createTransactionFactory(): SqliteTransactionFactory {
        return SqliteTransactionFactory(dataSource)
    }

    override fun TransactionFactory<*>.callTransaction() {
        this.readUncommitted {
        }
    }
}
