package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface FromTest<T : Table<T>> : SelectionTest<T> {
    @Test
    fun `from creates From`() {
        val select = select(columnA())

        val result = select.from(TestTable)

        result.select shouldBe select
        result.table shouldBe TestTable
    }

    @Test
    fun `from creates correct sql`() {
        val result = select(columnA(), columnB(), parameter().`as`("p"))
            .from(tableTest())
            .buildSelectQuery()
            .value
        result shouldBe "SELECT test.a, test.b, (?) as p FROM test"
    }

    @Test
    fun `from returns correct expressions`() {
        select(columnA(), columnB())
            .from(tableTest())
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(columnA(), columnB()))
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