package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable

object TestTable : PostgresTable<TestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
    val c = uuid("c")
    val d = timestamp("d")
}
