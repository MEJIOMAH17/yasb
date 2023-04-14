package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface JdbcTransactionAtLeastSerializable : JdbcTransactionAtLeastRepeatableRead

interface JdbcTransactionSerializable : JdbcTransactionAtLeastSerializable {
    companion object {
        val jdbcValue = Connection.TRANSACTION_SERIALIZABLE
    }
}

internal class JdbcTransactionSerializableImpl(override val connection: Connection) : JdbcTransactionSerializable
