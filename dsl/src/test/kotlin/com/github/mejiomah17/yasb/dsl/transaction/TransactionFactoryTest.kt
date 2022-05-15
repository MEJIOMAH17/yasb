package com.github.mejiomah17.yasb.dsl.transaction

import org.junit.jupiter.api.Test

abstract class TransactionFactoryTest {
    @Test
    fun `does not throw any on transaction block`() {
        createTransactionFactory().callTransaction()
    }

    abstract fun createTransactionFactory(): TransactionFactory
    abstract fun TransactionFactory.callTransaction()
}