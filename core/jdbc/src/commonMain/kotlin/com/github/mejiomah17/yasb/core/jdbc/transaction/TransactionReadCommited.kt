package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastReadCommitted
import com.github.mejiomah17.yasb.core.transaction.TransactionReadCommitted
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionReadCommitted : JdbcTransactionAtLeastReadCommitted,
    TransactionReadCommitted<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

interface JdbcTransactionAtLeastReadCommitted : JdbcTransactionAtLeastReadUncommitted,
    TransactionAtLeastReadCommitted<ResultSet, PreparedStatement>

internal class ImplJdbcTransactionReadCommitted(override val connection: Connection) :
    JdbcTransactionReadCommitted
