package com.github.mejiomah17.yasb.sqlite.jdbc

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass

abstract class SqliteTest {
    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeClass
        @JvmStatic
        fun init() {
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite:"
                }
            )
            dataSource.connection.use {
                it.createStatement().use {
                    it.executeUpdate(
                        """
                            CREATE TABLE test(
                               a string DEFAULT NULL,
                               b string DEFAULT NULL
                            )
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE FIRST(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE SECOND(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE THIRD(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                }
            }
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }
}
