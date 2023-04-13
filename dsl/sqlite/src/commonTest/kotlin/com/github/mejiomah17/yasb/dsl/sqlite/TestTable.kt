package com.github.mejiomah17.yasb.dsl.sqlite

import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable

object TestTable : SqliteJdbcTable<TestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
}
