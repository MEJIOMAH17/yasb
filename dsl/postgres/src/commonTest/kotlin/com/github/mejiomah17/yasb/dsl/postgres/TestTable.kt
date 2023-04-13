package com.github.mejiomah17.yasb.dsl.postgres

import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTable

object TestTable : PostgresJdbcTable<TestTable> {
    override val tableName: String = "test"
    val a = text("a")
    val b = text("b")
    val c = uuid("c")
    val d = timestamp("d")
    val e = doublePrecision("e")
}
