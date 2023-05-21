package com.github.mejiomah17.yasb.sqlite.jdbc.generator

import com.github.mejiomah17.yasb.sqlite.generator.SqliteColumnMetadataFactory
import com.github.mejiomah17.yasb.sqlite.generator.SqliteTableMetadataFactory
import com.github.mejiomah17.yasb.sqlite.generator.column.BigInt
import com.github.mejiomah17.yasb.sqlite.generator.column.Bool
import com.github.mejiomah17.yasb.sqlite.generator.column.Text
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class SqliteJdbcTableMetadataFactoryTest {
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

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    @Test
    fun `creates_table_definition`() {
        dataSource.connection.use {
            val table = SqliteTableMetadataFactory(
                SqliteJdbcTable::class.qualifiedName!!,
                SqliteColumnMetadataFactory()
            ).create(it, "test", null)
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
