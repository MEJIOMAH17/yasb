package com.github.mejiomah17.yasb.core.jdbc.transaction

import org.junit.Test

abstract class JdbcTransactionFactoryTest {
    @Test
    fun `does not throw any on transaction block`() {
        createTransactionFactory().callTransaction()
    }

    abstract fun createTransactionFactory(): JdbcTransactionFactory<*>
    abstract fun JdbcTransactionFactory<*>.callTransaction()
}
