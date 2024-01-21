package com.github.mejiomah17.yasb.core.dsl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test

class SelectTest {

    @Test
    fun `select_method_throws_exception_if_input_is_empty`() {
        shouldThrow<java.lang.IllegalArgumentException> {
            select<Any, Any>()
        }.message shouldBe "Select query should have at least 1 arg"
    }
}
