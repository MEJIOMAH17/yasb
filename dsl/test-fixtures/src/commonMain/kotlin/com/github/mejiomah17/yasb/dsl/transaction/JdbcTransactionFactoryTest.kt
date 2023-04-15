package com.github.mejiomah17.yasb.dsl.transaction

import org.junit.jupiter.api.Test
import java.sql.ResultSet

abstract class JdbcTransactionFactoryTest {
    @Test
    fun `does not throw any on transaction block`() {
        createTransactionFactory().callTransaction()
    }

    abstract fun createTransactionFactory(): TransactionFactory<*, ResultSet>
    abstract fun TransactionFactory<*, ResultSet>.callTransaction()
}
