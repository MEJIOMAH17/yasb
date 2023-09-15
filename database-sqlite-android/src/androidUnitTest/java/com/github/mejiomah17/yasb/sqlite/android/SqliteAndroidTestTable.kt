package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.sqlite.TestSqliteTable
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement

object SqliteAndroidTestTable :
    SqliteAndroidTable<SqliteAndroidTestTable>,
    TestSqliteTable<SqliteAndroidTestTable, Cursor, AndroidSqliteDriverStatement> {
    override val tableName: String = "test"
    override val a = text("a")
    override val b = text("b")
    override val c = long("c")
    override val d = bool("d")
    override val e = blob("e")
}
