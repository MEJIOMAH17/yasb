package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.postgres.jdbc.parameter.TextParameter
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class PostgresTest {
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
                        it.username = PostgresContainer.LOGIN
                        it.password = PostgresContainer.PASSWORD
                    },
                )
            dataSource.connection.use {
                it.createStatement().use {
                    it.execute(
                        """
                        CREATE TABLE test(
                           a text,
                           b text,
                           c uuid,
                           d timestamp,
                           e double precision
                        );
                        """.trimIndent(),
                    )
                    it.execute(
                        """
                        CREATE TABLE FIRST(
                           A text,
                           B text
                        );
                        """.trimIndent(),
                    )
                    it.execute(
                        """
                        CREATE TABLE SECOND(
                           A text,
                           B text
                        );
                        """.trimIndent(),
                    )
                    it.execute(
                        """
                        CREATE TABLE THIRD(
                           A text,
                           B text
                        );
                        """.trimIndent(),
                    )
                }
            }
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { container.close() }
            kotlin.runCatching { dataSource.close() }
        }
    }

    val databaseDialect = PostgresJdbcDatabaseDialect

    fun transactionFactory(): PostgresJdbcTransactionFactory = PostgresJdbcTransactionFactory(dataSource)

    fun <V> transaction(block: context(PostgresJdbcDatabaseDialect) JdbcTransactionRepeatableRead.() -> V): V =
        PostgresJdbcTransactionFactory(dataSource).repeatableRead {
            block()
        }

    fun parameter(): Parameter<String, ResultSet, PreparedStatement> = TextParameter("param")

    fun tableTest(): PostgresJdbcTestTable = PostgresJdbcTestTable

    fun executeSql(sql: String) {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute(sql)
            }
        }
    }
}
