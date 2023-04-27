package com.github.mejiomah17.yasb.dsl.transaction

import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class JdbcTransactionFactoryTest {
    @Test
    fun `does not throw any on transaction block`() {
        createTransactionFactory().callTransaction()
    }

    abstract fun createTransactionFactory(): TransactionFactory<*, ResultSet, PreparedStatement>
    abstract fun TransactionFactory<*, ResultSet, PreparedStatement>.callTransaction()
}
