package com.github.mejiomah17.yasb.dsl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class PostgresTest {
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
                               d timestamp
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

        @AfterAll
        @JvmStatic
        fun close() {
            kotlin.runCatching { container.close() }
            kotlin.runCatching { dataSource.close() }
        }
    }
}