package com.github.mejiomah17.yaksb.core.jdbc.transaction

import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.core.transaction.TransactionRepeatableRead
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionRepeatableRead :
    JdbcTransactionAtLeastRepeatableRead,
    TransactionRepeatableRead<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

interface JdbcTransactionAtLeastRepeatableRead :
    JdbcTransactionAtLeastReadCommitted,
    TransactionAtLeastRepeatableRead<ResultSet, PreparedStatement>

internal class ImplJdbcTransactionRepeatableRead(
    override val connection: Connection,
) : JdbcTransactionRepeatableRead
