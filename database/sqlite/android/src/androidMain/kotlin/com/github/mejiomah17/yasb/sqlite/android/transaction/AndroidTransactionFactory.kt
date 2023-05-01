@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastReadCommitted
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastReadUncommitted
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastSerializable
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.sqlite.android.SqliteAndroidDatabaseDialect

class AndroidTransactionFactory(
    private val database: SQLiteDatabase
) : TransactionFactory<Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidTransaction> {

    override fun dialect(): SqliteAndroidDatabaseDialect {
        return SqliteAndroidDatabaseDialect
    }

    override fun <V> serializable(block: context(SqliteAndroidDatabaseDialect, TransactionAtLeastSerializable<Cursor, (String) -> Unit>) AndroidTransaction.() -> V): V {
        return transaction({ AndroidSerializableTransactionImpl(it) }, block)
    }

    override fun <V> repeatableRead(block: context(SqliteAndroidDatabaseDialect, TransactionAtLeastRepeatableRead<Cursor, (String) -> Unit>) AndroidTransaction.() -> V): V {
        return serializable(block)
    }

    override fun <V> readCommitted(block: context(SqliteAndroidDatabaseDialect, TransactionAtLeastReadCommitted<Cursor, (String) -> Unit>) AndroidTransaction.() -> V): V {
        return serializable(block)
    }

    override fun <V> readUncommitted(block: context(SqliteAndroidDatabaseDialect, TransactionAtLeastReadUncommitted<Cursor, (String) -> Unit>) AndroidTransaction.() -> V): V {
        return serializable(block)
    }

    private fun <R, T : Transaction<Cursor, (String) -> Unit>> transaction(
        transactionCreator: (SQLiteDatabase) -> T,
        block: context(SqliteAndroidDatabaseDialect, T) (T) -> R
    ): R {
        // TODO retry
        try {
            database.beginTransaction()
            val result = dialect().run {
                val transaction = transactionCreator(database)
                block(dialect(), transaction, transaction)
            }
            database.setTransactionSuccessful()
            return result
        } finally {
            database.endTransaction()
        }
    }
}
