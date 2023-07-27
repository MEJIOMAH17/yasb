package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteDeleteTest
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidDeleteTest :
    SqliteAndroidTest(),
    SqliteDeleteTest<SqliteAndroidTestTable, Cursor, AndroidSqliteDriverStatement, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl>
