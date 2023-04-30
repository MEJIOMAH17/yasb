package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionReadCommitted
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionReadCommitted : JdbcTransaction, TransactionReadCommitted<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

internal class ImplJdbcTransactionReadCommitted(override val connection: Connection) :
    JdbcTransactionReadCommitted
