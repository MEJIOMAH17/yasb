package com.github.mejiomah17.yasb.dsl.generator

import com.github.mejiomah17.yasb.dsl.generator.column.Text
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class PostgresTableMetadataFactoryTest {
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
                               b text NOT NULL
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

    @Test
    fun `creates table definition`() {
        dataSource.connection.use {
            val table = PostgresTableMetadataFactory(PostgresColumnMetadataFactory()).create(it, "test",null)
            table.tableName shouldBe "test"
            table.columns shouldBe listOf(
                Text("a", nullable = true),
                Text("b", nullable = false)
            )

        }
    }
}
