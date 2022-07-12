package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface AtLeastRepeatableRead : AtLeastReadCommitted

interface TransactionRepeatableRead : Transaction, AtLeastRepeatableRead {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

internal class TransactionRepeatableReadImpl(override val connection: Connection) : TransactionRepeatableRead
