package com.github.mejiomah17.yasb.dsl.generator.postgres

import com.github.mejiomah17.yasb.dsl.generator.TableGenerator
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class PostgresTableGeneratorTest {
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
    fun `generates correct table definition`() {
        dataSource.connection.use {
            TableGenerator().generateTable(
                PostgresTableMetadataFactory(PostgresColumnMetadataFactory())
                    .create(it, "test", schemaPattern = null),
                "com.github.mejiomah17"
            ).run {
                content shouldBe
                    """
                            package com.github.mejiomah17

                            object TestTable : com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTable<TestTable> {
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
