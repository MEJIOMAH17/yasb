package com.github.mejiomah17.yasb.sqlite.android

object SqliteAndroidTestTable : SqliteAndroidTable<SqliteAndroidTestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
}
