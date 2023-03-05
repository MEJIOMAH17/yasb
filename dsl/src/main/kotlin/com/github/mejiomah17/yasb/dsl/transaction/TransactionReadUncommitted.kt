package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface AtLeastReadUncommitted

interface TransactionReadUncommitted : Transaction, AtLeastReadUncommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

internal class TransactionReadUncommittedImpl(override val connection: Connection) : TransactionReadUncommitted
