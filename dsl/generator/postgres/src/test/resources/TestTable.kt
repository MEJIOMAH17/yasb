package com.github.mejiomah17

object TestTable : com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable<TestTable> {
    override val tableName = "test"
    val a = textNullable("a")
    val b = text("b")
}
