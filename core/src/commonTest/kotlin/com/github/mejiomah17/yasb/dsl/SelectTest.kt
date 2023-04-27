package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryPart
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SelectTest {
    @Test
    fun `select method constructs Select`() {
        val expression = object : Expression<Any, Any> {
            override fun databaseType(): DatabaseType<Any, Any> {
                TODO("Not yet implemented")
            }

            override fun build(): QueryPart<Any> {
                TODO("Not yet implemented")
            }
        }

        val result = select(expression)

        result.expressions shouldBe listOf(expression)
    }

    @Test
    fun `select method throws exception if input is empty`() {
        shouldThrow<java.lang.IllegalArgumentException> {
            select<Any>()
        }.message shouldBe "Select query should have at least 1 arg"
    }
}
