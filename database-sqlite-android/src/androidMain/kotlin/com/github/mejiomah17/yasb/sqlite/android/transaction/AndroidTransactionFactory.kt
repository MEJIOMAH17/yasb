package com.github.mejiomah17.yaksb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.Repeater
import com.github.mejiomah17.yaksb.core.transaction.Transaction
import com.github.mejiomah17.yaksb.sqlite.android.SqliteAndroidDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.SQLException
import org.sqlite.database.sqlite.SQLiteDatabase

class AndroidTransactionFactory(
    private val database: SQLiteDatabase,
) {
    fun dialect(): SqliteAndroidDatabaseDialect = SqliteAndroidDatabaseDialect

    // TODO write tests is it really serializable
    fun <V> serializable(
        repeater: Repeater<V> = defaultRepeater(),
        block: context(SqliteAndroidDatabaseDialect)
        AndroidSerializableTransactionImpl.() -> V,
    ): V = transaction(repeater, { AndroidSerializableTransactionImpl(it) }, block)

    fun <V> defaultRepeater(): Repeater<V> = Repeater.repeatOn<V, SQLException>(3)

    private fun <R, T : Transaction<Cursor, AndroidSqliteDriverStatement>> transaction(
        repeater: Repeater<R>,
        transactionCreator: (SQLiteDatabase) -> T,
        block: context(SqliteAndroidDatabaseDialect)
        (T) -> R,
    ): R =
        repeater.repeat {
            try {
                database.beginTransaction()
                val result =
                    dialect().run {
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
