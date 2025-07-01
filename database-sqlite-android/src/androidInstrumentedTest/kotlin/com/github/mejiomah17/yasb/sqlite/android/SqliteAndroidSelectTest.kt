package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.dsl.SelectTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidSelectTest :
    SelectTest<SqliteAndroidTestTable, Cursor, AndroidSqliteDriverStatement, SqliteAndroidDatabaseDialect, AndroidSerializableTransactionImpl>,
    SqliteAndroidTest() {
    override fun initSqlScripts(): List<String> {
        return emptyList()
    }
}
