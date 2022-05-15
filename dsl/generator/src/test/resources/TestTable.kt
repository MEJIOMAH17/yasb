package com.github.mejiomah17

object TestTable : com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable<TestTable> {
    override val tableName = "test"
    val x = 42
    val y = 42
}
