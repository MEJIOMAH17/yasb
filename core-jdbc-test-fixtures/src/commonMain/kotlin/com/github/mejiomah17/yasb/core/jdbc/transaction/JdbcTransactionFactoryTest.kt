package com.github.mejiomah17.yasb.core.jdbc.transaction

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.sql.SQLException

abstract class JdbcTransactionFactoryTest {
    @Test
    fun `returns value from transaction`() {
        val result = createTransactionFactory().callTransaction {
            42
        }

        result.shouldBe(42)
    }

    @Test
    fun `retries 3 times by default`() {
        var counter = 0

        shouldThrow<SQLException> {
            createTransactionFactory().callTransaction {
                counter++
                throw SQLException()
            }
        }

        counter.shouldBe(3)
    }

    abstract fun createTransactionFactory(): JdbcTransactionFactory<*>
    abstract fun JdbcTransactionFactory<*>.callTransaction(block: () -> Any): Any
}
