package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastSerializable
import com.github.mejiomah17.yasb.core.transaction.TransactionSerializable
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionSerializable : JdbcTransactionAtLeastSerializable,
    TransactionSerializable<ResultSet, PreparedStatement> {
    companion object {
        val jdbcValue = Connection.TRANSACTION_SERIALIZABLE
    }
}

interface JdbcTransactionAtLeastSerializable :
    JdbcTransactionAtLeastRepeatableRead,
    TransactionAtLeastSerializable<ResultSet, PreparedStatement>

internal class JdbcTransactionSerializableImpl(override val connection: Connection) : JdbcTransactionSerializable
