package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface JdbcTransactionAtLeastRepeatableRead : JdbcTransactionAtLeastReadCommitted

interface JdbcTransactionRepeatableRead : JdbcTransactionAtLeastRepeatableRead {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}

internal class ImplJdbcTransactionRepeatableRead(override val connection: Connection) :
    JdbcTransactionRepeatableRead
