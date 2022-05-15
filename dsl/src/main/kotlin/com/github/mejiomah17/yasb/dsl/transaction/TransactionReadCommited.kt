package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface AtLeastReadCommitted

interface TransactionReadCommitted : Transaction, AtLeastReadUncommitted, AtLeastReadCommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

internal class TransactionReadCommittedImpl(override val connection: Connection) : TransactionReadCommitted
