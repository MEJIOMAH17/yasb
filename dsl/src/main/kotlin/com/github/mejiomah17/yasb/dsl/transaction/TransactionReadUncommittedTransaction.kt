package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastReadUncommitted : Transaction

interface TransactionReadUncommittedTransaction : TransactionAtLeastReadUncommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

internal class TransactionReadUncommittedImplTransaction(override val connection: Connection) :
    TransactionReadUncommittedTransaction
