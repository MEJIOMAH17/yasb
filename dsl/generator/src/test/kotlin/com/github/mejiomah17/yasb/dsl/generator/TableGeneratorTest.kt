package com.github.mejiomah17.yasb.dsl.generator

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TableGeneratorTest {
    @Test
    fun `creates correct code`() {
        TableGenerator().generateTable(
            table = TableMetadata(
                tableName = "test",
                columns = listOf(
                    TestColumn("x"),
                    TestColumn("y")
                )
            ),
            classPackage = "com.github.mejiomah17"
        ).run {
            content shouldBe this::class.java.classLoader.getResourceAsStream("TestTable.kt").bufferedReader()
                .readText()
            fileName shouldBe "TestTable.kt"
        }
    }

    @Test
    fun `do not duplicate table suffix correct code`() {
        val result = TableGenerator().generateTable(
            table = TableMetadata(
                tableName = "test_table",
                columns = listOf(
                    TestColumn("x"),
                    TestColumn("y")
                )
            ),
            classPackage = "com.github.mejiomah17"
        )
        result.fileName shouldBe "TestTable.kt"
        result.content shouldBe this::class.java.classLoader.getResourceAsStream("TestTable2.kt").bufferedReader()
            .readText()
    }

    private class TestColumn(val name: String) : ColumnMetadata {
        override fun columnDefinition(): String {
            return "val $name = 42"
        }
    }
}