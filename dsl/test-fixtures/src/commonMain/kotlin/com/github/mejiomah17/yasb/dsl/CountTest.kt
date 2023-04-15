@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.dsl.alias.`as`
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface CountTest<T : Table<T, S>, S, D : DatabaseDialect<S>> : SelectionTest<T, S, D> {
    @Test
    fun `count returns count of elements`() {
        transactionFactory().readCommitted {
            val count = count(columnA()).`as`("aCount")
            val from: From<S> = select(count)
                .from(tableTest())
            from
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}
