package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastReadUncommitted
import com.github.mejiomah17.yasb.core.transaction.TransactionReadUncommitted
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionReadUncommitted :
    JdbcTransactionAtLeastReadUncommitted,
    TransactionReadUncommitted<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

interface JdbcTransactionAtLeastReadUncommitted :
    JdbcTransaction,
    TransactionAtLeastReadUncommitted<ResultSet, PreparedStatement>

internal class ImplJdbcTransactionReadUncommitted(override val connection: Connection) :
    JdbcTransactionReadUncommitted
