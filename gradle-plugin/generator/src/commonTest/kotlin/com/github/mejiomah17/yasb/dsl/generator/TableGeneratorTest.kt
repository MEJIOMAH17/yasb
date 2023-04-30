package com.github.mejiomah17.yasb.dsl.generator

import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TableGeneratorTest {
    @Test
    fun `creates correct code`() {
        TableGenerator().generateTable(
            table = TableMetadata(
                tableName = "test",
                tableClass = Table::class,
                columns = listOf(
                    TestColumn("x"),
                    TestColumn("y")
                )
            ),
            classPackage = "com.github.mejiomah17"
        ).run {
            content shouldBe """
                        package com.github.mejiomah17

                        object TestTable : com.github.mejiomah17.yasb.core.ddl.Table<TestTable> {
                            override val tableName = "test"
                            val x = 42
                            val y = 42
                        }

            """.trimIndent()
            fileName shouldBe "TestTable.kt"
        }
    }

    @Test
    fun `do not duplicate table suffix correct code`() {
        val result = TableGenerator().generateTable(
            table = TableMetadata(
                tableName = "test_table",
                tableClass = Table::class,
                columns = listOf(
                    TestColumn("x"),
                    TestColumn("y")
                )
            ),
            classPackage = "com.github.mejiomah17"
        )
        result.fileName shouldBe "TestTable.kt"
        result.content shouldBe """
                    package com.github.mejiomah17

                    object TestTable : com.github.mejiomah17.yasb.core.ddl.Table<TestTable> {
                        override val tableName = "test_table"
                        val x = 42
                        val y = 42
                    }

        """.trimIndent()
    }

    private class TestColumn(val name: String) : ColumnMetadata {
        override fun columnDefinition(): String {
            return "val $name = 42"
        }
    }
}
