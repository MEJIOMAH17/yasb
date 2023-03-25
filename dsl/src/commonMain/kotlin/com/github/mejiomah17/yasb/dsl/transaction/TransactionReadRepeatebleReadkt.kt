package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastRepeatableRead : TransactionAtLeastReadCommitted

interface TransactionRepeatableRead : TransactionAtLeastRepeatableRead {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

internal class ImplTransactionRepeatableRead(override val connection: Connection) :
    TransactionRepeatableRead
