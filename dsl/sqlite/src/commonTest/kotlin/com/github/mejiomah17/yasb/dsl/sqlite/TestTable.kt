package com.github.mejiomah17.yasb.dsl.sqlite

import com.github.mejiomah17.yasb.core.sqlite.ddl.SqliteTable

object TestTable : SqliteTable<TestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
}
