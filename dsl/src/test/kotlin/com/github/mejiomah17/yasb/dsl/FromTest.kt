package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.dsl.alias.`as`
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface FromTest<T : Table<T>> : SelectionTest<T> {
    @Test
    fun `from creates From`() {
        val select = select(columnA())

        val result = select.from(TestTable)

        result.select shouldBe select
        result.source shouldBe TestTable
    }

    @Test
    fun `from creates From for aliased table`() {
        val select = select(columnA())
        val table = TestTable.`as`("xxx")
        val result = select.from(table)

        result.select shouldBe select
        result.source shouldBe table
    }

    @Test
    fun `from creates correct sql`() {
        val result = select(columnA(), columnB(), parameter().`as`("p"))
            .from(tableTest())
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT test.a, test.b, (?) AS p FROM test"
    }

    @Test
    fun `from creates correct sql for aliased table`() {
        val table = tableTest().`as`("xxx")
        val result = select(table[columnA()], table[columnB()], parameter().`as`("p"))
            .from(table)
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT xxx.a, xxx.b, (?) AS p FROM test AS xxx"
    }

    @Test
    fun `from returns correct expressions`() {
        select(columnA(), columnB())
            .from(tableTest())
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(columnA(), columnB()))
    }

    @Test
    fun `from returns correct expressions for aliased table`() {
        val table = tableTest().`as`("xxx")
        val aColumn = table[columnA()]
        val bColumn = table[columnB()]
        select(aColumn, bColumn)
            .from(table)
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(aColumn, bColumn))
    }

    @Test
    fun `from returns columns`() {
        transactionFactory().readUncommitted {
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "the a"
            row[columnB()] shouldBe "the b"
        }
    }

    @Test
    fun `from returns columns for aliased table`() {
        val table = tableTest().`as`("xxx")
        val aColumn = table[columnA()]
        val bColumn = table[columnB()]
        transactionFactory().readUncommitted {
            val row = select(aColumn, bColumn)
                .from(table)
                .execute()
                .single()
            row[aColumn] shouldBe "the a"
            row[bColumn] shouldBe "the b"
        }
    }

    @Test
    fun `from returns parameter`() {
        val param = parameter().`as`("p")
        transactionFactory().readUncommitted {
            val row = select(param)
                .from(tableTest())
                .execute()
                .single()
            row[param] shouldBe "param"
        }
    }

    @Test
    fun `from returns correct parameters`() {
        val param = parameter()
        select(param.`as`("p"))
            .from(tableTest())
            .buildSelectQuery()
            .parameters.single().shouldBe(param)
    }
}