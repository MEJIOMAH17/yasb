@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yasb.core.Repeater
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.sqlite.android.SqliteAndroidDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.SQLException
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

    override fun <V> serializable(
        repeater: Repeater<V>,
        block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V
    ): V {
        return transaction(repeater, { AndroidSerializableTransactionImpl(it) }, block)
    }

    override fun <V> repeatableRead(
        repeater: Repeater<V>,
        block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V
    ): V {
        return serializable(repeater, block)
    }

    override fun <V> readCommitted(
        repeater: Repeater<V>,
        block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V
    ): V {
        return serializable(repeater, block)
    }

    override fun <V> readUncommitted(
        repeater: Repeater<V>,
        block: context(SqliteAndroidDatabaseDialect) AndroidSerializableTransactionImpl.() -> V
    ): V {
        return serializable(repeater, block)
    }

    override fun <V> defaultRepeater(): Repeater<V> {
        return Repeater.repeatOn<V, SQLException>(3)
    }

    private fun <R, T : Transaction<Cursor, AndroidSqliteDriverStatement>> transaction(
        repeater: Repeater<R>,
        transactionCreator: (SQLiteDatabase) -> T,
        block: context(SqliteAndroidDatabaseDialect) (T) -> R
    ): R = repeater.repeat {
        try {
            database.beginTransaction()
            val result = dialect().run {
                val transaction = transactionCreator(database)
                block(dialect(), transaction)
            }
            database.setTransactionSuccessful()
            return@repeat result
        } finally {
            database.endTransaction()
        }
    }
}
