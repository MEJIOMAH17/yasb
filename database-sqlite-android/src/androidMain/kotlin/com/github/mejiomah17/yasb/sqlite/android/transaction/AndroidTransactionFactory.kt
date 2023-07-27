@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.sqlite.android.SqliteAndroidDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.sqlite.SQLiteDatabase

class AndroidTransactionFactory(
    private val database: SQLiteDatabase
) : TransactionFactory<
        Cursor,
        AndroidSqliteDriverStatement,
        SqliteAndroidDatabaseDialect,
        AndroidSerializableTransactionImpl,
        AndroidSerializableTransactionImpl,
        AndroidSerializableTransactionImpl,
        AndroidSerializableTransactionImpl
        > {

    override fun dialect(): SqliteAndroidDatabaseDialect {
        return SqliteAndroidDatabaseDialect
    }

    override fun <V> serializable(block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V): V {
        return transaction({ AndroidSerializableTransactionImpl(it) }, block)
    }

    override fun <V> repeatableRead(block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V): V {
        return serializable(block)
    }

    override fun <V> readCommitted(block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V): V {
        return serializable(block)
    }

    override fun <V> readUncommitted(block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V): V {
        return serializable(block)
    }

    private fun <R, T : Transaction<Cursor, AndroidSqliteDriverStatement>> transaction(
        transactionCreator: (SQLiteDatabase) -> T,
        block: context(SqliteAndroidDatabaseDialect) (T) -> R
    ): R {
        // TODO retry
        try {
            database.beginTransaction()
            val result = dialect().run {
                val transaction = transactionCreator(database)
                block(dialect(), transaction)
            }
            database.setTransactionSuccessful()
            return result
        } finally {
            database.endTransaction()
        }
    }
}
