package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlin.test.Test

interface TransactionTest<
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> {
    @Test
    fun returns_value_from_transaction() {
        val result =
            readCommitedTransaction {
                42
            }

        result.shouldBe(42)
    }

    @Test
    fun retries_3_times_by_default() {
        var counter = 0
        var exception = exception()
        shouldThrow<Exception> {
            readCommitedTransaction {
                counter++
                throw exception
            }
        }.shouldBeSameInstanceAs(exception)

        counter.shouldBe(3)
    }

    fun <V> readCommitedTransaction(block: context(DIALECT) TRANSACTION.() -> V): V

    fun exception(): Exception
}
