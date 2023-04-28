package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.transaction.TransactionReadUncommitted
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JdbcTransactionReadUncommitted : JdbcTransaction, TransactionReadUncommitted<ResultSet, PreparedStatement> {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

internal class ImplJdbcTransactionReadUncommitted(override val connection: Connection) :
    JdbcTransactionReadUncommitted
