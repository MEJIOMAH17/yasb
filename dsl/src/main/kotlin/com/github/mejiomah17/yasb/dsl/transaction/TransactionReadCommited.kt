package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastReadCommitted : TransactionAtLeastReadUncommitted

interface TransactionReadCommittedTransaction : TransactionAtLeastReadCommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

internal class TransactionReadCommittedImplTransaction(override val connection: Connection) :
    TransactionReadCommittedTransaction
