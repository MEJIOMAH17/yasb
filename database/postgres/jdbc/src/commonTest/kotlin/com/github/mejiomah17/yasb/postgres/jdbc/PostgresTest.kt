package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
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
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = container.jdbcUrl
                    it.username = PostgresContainer.LOGIN
                    it.password = PostgresContainer.PASSWORD
                }
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
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE FIRST(
                               A text,
                               B text
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE SECOND(
                               A text,
                               B text
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE THIRD(
                               A text,
                               B text
                            );
                        """.trimIndent()
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

    fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }

    fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    fun tableTest(): PostgresJdbcTestTable {
        return PostgresJdbcTestTable
    }

    fun executeSql(sql: String) {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute(sql)
            }
        }
    }
}
