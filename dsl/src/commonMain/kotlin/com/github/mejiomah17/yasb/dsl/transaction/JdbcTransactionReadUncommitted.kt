package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface JdbcTransactionAtLeastReadUncommitted : JdbcTransaction

interface JdbcTransactionReadUncommitted : JdbcTransactionAtLeastReadUncommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

internal class ImplJdbcTransactionReadUncommitted(override val connection: Connection) :
    JdbcTransactionReadUncommitted
