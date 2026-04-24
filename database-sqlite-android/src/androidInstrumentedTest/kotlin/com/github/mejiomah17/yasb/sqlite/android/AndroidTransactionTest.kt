package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.dsl.TransactionTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl
import org.sqlite.database.SQLException
import org.sqlite.database.sqlite.SQLiteException

class AndroidTransactionTest :
    SqliteAndroidTest(),
    TransactionTest<
        Cursor,
        AndroidSqliteDriverStatement,
        SqliteAndroidDatabaseDialect,
        AndroidSerializableTransactionImpl,
    > {
    override fun exception(): Exception = SQLException()
}
