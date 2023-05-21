package com.github.mejiomah17.yasb.sqlite.jdbc.generator

import com.github.mejiomah17.yasb.dsl.generator.TableGenerator
import com.github.mejiomah17.yasb.sqlite.generator.SqliteColumnMetadataFactory
import com.github.mejiomah17.yasb.sqlite.generator.SqliteTableMetadataFactory
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class SqliteJdbcTableGeneratorTest {
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
                               a text NULL,
                               b text NOT NULL
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
    fun `generates_correct_table_definition`() {
        dataSource.connection.use {
            TableGenerator().generateTable(
                SqliteTableMetadataFactory(SqliteJdbcTable::class.qualifiedName!!, SqliteColumnMetadataFactory())
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
