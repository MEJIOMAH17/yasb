package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastReadCommitted : TransactionAtLeastReadUncommitted

interface TransactionReadCommitted : TransactionAtLeastReadCommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

internal class ImplTransactionReadCommitted(override val connection: Connection) :
    TransactionReadCommitted
