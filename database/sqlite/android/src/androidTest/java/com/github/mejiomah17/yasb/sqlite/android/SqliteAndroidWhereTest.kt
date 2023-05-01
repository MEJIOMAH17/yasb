package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteWhereTest
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransaction

class SqliteAndroidWhereTest :
    SqliteAndroidTest(),
    SqliteWhereTest<SqliteAndroidTestTable, Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidTransaction>
