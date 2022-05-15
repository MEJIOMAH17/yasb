package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface AtLeastSerializable

interface TransactionSerializable : Transaction, AtLeastReadUncommitted, AtLeastReadCommitted, AtLeastRepeatableRead,
    AtLeastSerializable {
    companion object {
        val jdbcValue = Connection.TRANSACTION_SERIALIZABLE
    }
}

internal class TransactionSerializableImpl(override val connection: Connection) : TransactionSerializable