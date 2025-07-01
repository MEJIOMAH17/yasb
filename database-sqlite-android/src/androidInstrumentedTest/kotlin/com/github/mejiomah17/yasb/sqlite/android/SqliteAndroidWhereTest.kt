package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.sqlite.SqliteWhereTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidWhereTest :
    SqliteAndroidTest(),
    SqliteWhereTest<SqliteAndroidTestTable, Cursor, AndroidSqliteDriverStatement, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl>
