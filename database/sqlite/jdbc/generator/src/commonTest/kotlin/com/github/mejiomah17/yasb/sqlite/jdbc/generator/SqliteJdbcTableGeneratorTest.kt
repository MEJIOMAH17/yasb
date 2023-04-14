package com.github.mejiomah17.yasb.sqlite.jdbc.generator

import com.github.mejiomah17.yasb.dsl.generator.TableGenerator
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SqliteJdbcTableGeneratorTest {
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
                               a text NULL,
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
            kotlin.runCatching { dataSource.close() }
        }
    }

    @Test
    fun `generates correct table definition`() {
        dataSource.connection.use {
            TableGenerator().generateTable(
                SqliteJdbcTableMetadataFactory(SqliteJdbcColumnMetadataFactory())
                    .create(it, "test", schemaPattern = null),
                "com.github.mejiomah17"
            ).run {
                content shouldBe
                    """
                            package com.github.mejiomah17

                            object TestTable : com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable<TestTable> {
                                override val tableName = "test"
                                val a = textNullable("a")
                                val b = text("b")
                            }

                    """.trimIndent()
                fileName shouldBe "TestTable.kt"
            }
        }
    }
}