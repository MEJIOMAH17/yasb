package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.dsl.alias.`as`
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface CountTest<T : Table<T>> : SelectionTest<T> {
    @Test
    fun `count returns count of elements`() {
        transactionFactory().readCommitted {
            val count = count(columnA()).`as`("aCount")
            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}