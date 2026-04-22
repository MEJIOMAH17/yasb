package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.dsl.SelectTest
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.transaction.AndroidSerializableTransactionImpl

class SqliteAndroidSelectTest :
    SqliteAndroidTest(),
    SelectTest<
        SqliteAndroidTestTable,
        Cursor,
        AndroidSqliteDriverStatement,
        SqliteAndroidDatabaseDialect,
        AndroidSerializableTransactionImpl,
    > {
    override fun initSqlScripts(): List<String> = emptyList()
}
