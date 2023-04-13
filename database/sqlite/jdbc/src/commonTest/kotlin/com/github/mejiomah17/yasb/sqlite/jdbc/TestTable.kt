package com.github.mejiomah17.yasb.sqlite.jdbc

object TestTable : SqliteJdbcTable<TestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
}
