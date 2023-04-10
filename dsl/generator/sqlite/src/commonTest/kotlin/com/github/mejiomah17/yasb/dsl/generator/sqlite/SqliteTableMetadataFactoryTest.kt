package com.github.mejiomah17.yasb.dsl.generator.sqlite

import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.BigInt
import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.Bool
import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.Text
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SqliteTableMetadataFactoryTest {
    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeAll
        @JvmStatic
        fun init() {
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite:"
                }
            )
            dataSource.connection.use {
                it.createStatement().use {
                    it.execute(
                        """
                            CREATE TABLE test(
                               a text,
                               b text NOT NULL,
                               c bigint,
                               d bigint NOT NULL,
                               e bool,
                               f bool NOT NULL
                            );
                        """.trimIndent()
                    )
                }
            }
        }

        @AfterAll
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    @Test
    fun `creates table definition`() {
        dataSource.connection.use {
            val table = SqliteTableMetadataFactory(SqliteColumnMetadataFactory()).create(it, "test", null)
            table.tableName shouldBe "test"
            table.columns shouldBe listOf(
                Text("a", nullable = true),
                Text("b", nullable = false),
                BigInt("c", nullable = true),
                BigInt("d", nullable = false),
                Bool("e", nullable = true),
                Bool("f", nullable = false)
            )
        }
    }
}
