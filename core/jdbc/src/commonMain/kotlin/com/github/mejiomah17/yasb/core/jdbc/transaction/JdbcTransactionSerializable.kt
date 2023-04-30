package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionSerializable
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionSerializable : JdbcTransaction, TransactionSerializable<ResultSet, PreparedStatement> {
    companion object {
        val jdbcValue = Connection.TRANSACTION_SERIALIZABLE
    }
}

internal class JdbcTransactionSerializableImpl(override val connection: Connection) : JdbcTransactionSerializable
