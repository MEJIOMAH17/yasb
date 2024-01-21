package com.github.mejiomah17.yasb.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test

class RepeaterTest {

    @Test
    fun `does not repeat unexpected exceptions`() {
        // given
        var counter = 0
        val repeater = Repeater.repeatOn<Unit, NullPointerException>(2)

        // when
        shouldThrow<IllegalStateException> {
            repeater.repeat {
                counter++
                throw IllegalStateException()
            }
        }

        // then
        counter.shouldBe(1)
    }

    @Test
    fun `repeats 2 times before throw exception`() {
        // given
        var counter = 0
        val repeater = Repeater.repeatOn<Unit, IllegalStateException>(2)

        // when
        shouldThrow<IllegalStateException> {
            repeater.repeat {
                counter++
                throw IllegalStateException()
            }
        }

        // then
        counter.shouldBe(2)
    }

    @Test
    fun `validates that argument is positive`() {
        shouldThrow<IllegalStateException> {
            Repeater.repeatOn<Unit, IllegalStateException>(0)
        }.message.shouldBe("count must be greater than 0. Actual value is 0")
    }

    @Test
    fun `executes 2 times`() {
        // given
        var counter = 0
        val repeater = Repeater.repeatOn<Unit, IllegalStateException>(2)

        // when
        repeater.repeat {
            counter++
            if (counter == 1) {
                throw IllegalStateException()
            }
        }

        // then
        counter.shouldBe(2)
    }

    @Test
    fun `executes 1 time if successful`() {
        // given
        var counter = 0
        val repeater = Repeater.repeatOn<Unit, IllegalStateException>(2)

        // when
        repeater.repeat {
            counter++
        }

        // then
        counter.shouldBe(1)
    }

    @Test
    fun `returns block result`() {
        // given
        val repeater = Repeater.repeatOn<Int, IllegalStateException>(2)

        // when
        val result = repeater.repeat {
            42
        }

        // then
        result.shouldBe(42)
    }
}
