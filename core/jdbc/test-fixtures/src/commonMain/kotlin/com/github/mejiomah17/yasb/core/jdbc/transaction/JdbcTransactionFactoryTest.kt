package com.github.mejiomah17.yasb.core.jdbc.transaction

import org.junit.Test

abstract class JdbcTransactionFactoryTest {
    @Test
    fun `does_not_throw_any_on_transaction_block`() {
        createTransactionFactory().callTransaction()
    }

    abstract fun createTransactionFactory(): JdbcTransactionFactory<*>
    abstract fun JdbcTransactionFactory<*>.callTransaction()
}
