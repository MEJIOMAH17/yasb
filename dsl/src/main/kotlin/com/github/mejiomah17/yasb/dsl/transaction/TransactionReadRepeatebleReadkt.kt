package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastRepeatableRead : TransactionAtLeastReadCommitted

interface TransactionRepeatableReadTransaction : TransactionAtLeastRepeatableRead {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

internal class TransactionRepeatableReadImplTransaction(override val connection: Connection) :
    TransactionRepeatableReadTransaction
