package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.dsl.TestTable

object SqliteAndroidTestTable :
    SqliteAndroidTable<SqliteAndroidTestTable>,
    TestTable<SqliteAndroidTestTable, Cursor, (String) -> Unit> {
    override val tableName: String = "test"
    override val a = text("a")
    override val b = text("b")
}
