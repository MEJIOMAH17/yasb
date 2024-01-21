package com.github.mejiomah17.yasb.postgres.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactory
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactoryTest
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresContainer
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresContainer.Companion.LOGIN
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresContainer.Companion.PASSWORD
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass

class PostgresJdbcJdbcTransactionFactoryTest : JdbcTransactionFactoryTest() {
    companion object {
        lateinit var dataSource: HikariDataSource
        lateinit var container: PostgresContainer

        @BeforeClass
        @JvmStatic
        fun init() {
            container = PostgresContainer()
            container.start()
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = container.jdbcUrl
                    it.username = LOGIN
                    it.password = PASSWORD
                }
            )
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { container.close() }
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun createTransactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    override fun JdbcTransactionFactory<*>.callTransaction(block: () -> Any): Any {
        return this.readUncommitted {
            block()
        }
    }
}
