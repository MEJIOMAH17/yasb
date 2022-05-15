package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryWithParameters
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SelectTest {
    @Test
    fun `select method constructs Select`() {
        val expression = object : Expression<Any> {
            override fun databaseType(): DatabaseType<Any> {
                TODO("Not yet implemented")
            }

            override fun build(): QueryWithParameters {
                TODO("Not yet implemented")
            }
        }

        val result = select(expression)

        result.expressions shouldBe listOf(expression)
    }

    @Test
    fun `select method throws exception if input is empty`() {
        shouldThrow<java.lang.IllegalArgumentException> {
            select()
        }.message shouldBe "Select query should have at least 1 arg"
    }
}