package com.github.mejiomah17.yasb.dsl.transaction

import java.sql.Connection

interface TransactionAtLeastReadUncommitted : Transaction

interface TransactionReadUncommitted : TransactionAtLeastReadUncommitted {
    companion object {
        val jdbcLevel = Connection.TRANSACTION_READ_UNCOMMITTED
    }
}

internal class ImplTransactionReadUncommitted(override val connection: Connection) :
    TransactionReadUncommitted
