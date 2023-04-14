package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface JdbcTransactionAtLeastReadCommitted : JdbcTransactionAtLeastReadUncommitted

interface JdbcTransactionReadCommitted : JdbcTransactionAtLeastReadCommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_COMMITTED
    }
}

internal class ImplJdbcTransactionReadCommitted(override val connection: Connection) :
    JdbcTransactionReadCommitted
