package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test

interface TransactionFactoryTest {

    @Test
    fun returns_value_from_transaction() {
        val result = transactionFactory().serializable {
            42
        }

        result.shouldBe(42)
    }

    @Test
    fun retries_3_times_by_default() {
        var counter = 0
        var exception = exception()
        shouldThrow<Exception> {
            transactionFactory().serializable {
                counter++
                throw exception
            }
        }.shouldBeSameInstanceAs(exception)

        counter.shouldBe(3)
    }

    fun transactionFactory(): TransactionFactory<*, *, *, *, *, *, *>
    fun exception(): Exception
}
