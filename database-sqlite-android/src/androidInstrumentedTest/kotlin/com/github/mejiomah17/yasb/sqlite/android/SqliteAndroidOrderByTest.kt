package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.sqlite.SqliteOrderByTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidOrderByTest :
    SqliteAndroidTest(),
    SqliteOrderByTest<
        SqliteAndroidTestTable,
        Cursor,
        AndroidSqliteDriverStatement,
        SqliteAndroidDatabaseDialect,
        AndroidSerializableTransactionImpl,
    >
