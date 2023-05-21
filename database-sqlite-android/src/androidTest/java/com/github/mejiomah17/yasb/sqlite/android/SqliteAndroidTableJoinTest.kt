package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteTableJoinTest
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidTableJoinTest :
    SqliteAndroidTest(),
    SqliteTableJoinTest<SqliteAndroidTest.SecondTable, Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl>
