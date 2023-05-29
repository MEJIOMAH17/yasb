package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test

class SelectTest {
    @Test
    fun `select_method_constructs_Select`() {
        val expression = object : Expression<Any, Any, Any> {
            override fun databaseType(): DatabaseType<Any, Any, Any> {
                TODO("Not yet implemented")
            }

            override fun sql(): String {
                TODO("Not yet implemented")
            }

            override fun parameters(): List<Parameter<*, Any, Any>> {
                TODO("Not yet implemented")
            }
        }

        val result = select(expression)

        result.expressions shouldBe listOf(expression)
    }

    @Test
    fun `select_method_throws_exception_if_input_is_empty`() {
        shouldThrow<java.lang.IllegalArgumentException> {
            select<Any, Any>()
        }.message shouldBe "Select query should have at least 1 arg"
    }
}
