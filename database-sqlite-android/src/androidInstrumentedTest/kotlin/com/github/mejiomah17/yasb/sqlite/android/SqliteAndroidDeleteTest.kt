package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.sqlite.SqliteDeleteTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidDeleteTest :
    SqliteAndroidTest(),
    SqliteDeleteTest<
        SqliteAndroidTestTable,
        Cursor,
        AndroidSqliteDriverStatement,
        SqliteAndroidDatabaseDialect,
        AndroidSerializableTransactionImpl,
    >
