package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionRepeatableRead
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionRepeatableRead : JdbcTransaction, TransactionRepeatableRead<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

internal class ImplJdbcTransactionRepeatableRead(override val connection: Connection) :
    JdbcTransactionRepeatableRead
