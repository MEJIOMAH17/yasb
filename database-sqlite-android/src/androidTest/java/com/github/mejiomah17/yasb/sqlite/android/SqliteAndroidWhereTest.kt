package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.SqliteWhereTest
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidWhereTest :
    SqliteAndroidTest(),
    SqliteWhereTest<SqliteAndroidTestTable, Cursor, AndroidSqliteDriverStatement, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl> {

}
