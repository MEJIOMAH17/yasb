package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteInsertTest
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidInsertTest :
    SqliteAndroidTest(),
    SqliteInsertTest<SqliteAndroidTestTable, Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl>
