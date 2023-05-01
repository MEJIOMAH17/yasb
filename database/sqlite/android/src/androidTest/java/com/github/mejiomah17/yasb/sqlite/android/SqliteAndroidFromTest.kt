package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteFromTest
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransaction

class SqliteAndroidFromTest :
    SqliteAndroidTest(),
    SqliteFromTest<SqliteAndroidTestTable, Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidTransaction>
