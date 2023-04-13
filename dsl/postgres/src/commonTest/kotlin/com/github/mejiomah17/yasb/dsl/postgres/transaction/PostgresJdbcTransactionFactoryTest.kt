package com.github.mejiomah17.yasb.dsl.postgres.transaction

import com.github.mejiomah17.yasb.dsl.postgres.PostgresContainer
import com.github.mejiomah17.yasb.dsl.postgres.PostgresContainer.Companion.LOGIN
import com.github.mejiomah17.yasb.dsl.postgres.PostgresContainer.Companion.PASSWORD
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactoryTest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

class PostgresJdbcTransactionFactoryTest : TransactionFactoryTest() {
    companion object {
        lateinit var dataSource: HikariDataSource
        lateinit var container: PostgresContainer

        @BeforeAll
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

        @AfterAll
        @JvmStatic
        fun close() {
            kotlin.runCatching { container.close() }
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun createTransactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    override fun TransactionFactory<*>.callTransaction() {
        this.readUncommitted {
        }
    }
}
