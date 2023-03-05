package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface AtLeastSerializable : AtLeastRepeatableRead

interface TransactionSerializable : Transaction, AtLeastSerializable {
    companion object {
        val jdbcValue = Connection.TRANSACTION_SERIALIZABLE
    }
}

internal class TransactionSerializableImpl(override val connection: Connection) : TransactionSerializable
