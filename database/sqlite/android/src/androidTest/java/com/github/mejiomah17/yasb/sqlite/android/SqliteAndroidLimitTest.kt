package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteLimitTest
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransaction

class SqliteAndroidLimitTest :
    SqliteAndroidTest(),
    SqliteLimitTest<SqliteAndroidTestTable, Cursor, (String) -> Unit, SqliteAndroidDatabaseDialect, AndroidTransaction>
