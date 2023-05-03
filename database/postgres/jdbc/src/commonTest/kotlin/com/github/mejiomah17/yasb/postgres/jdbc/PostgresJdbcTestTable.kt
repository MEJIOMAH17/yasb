package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.dsl.TestTable
import java.sql.PreparedStatement
import java.sql.ResultSet

object PostgresJdbcTestTable :
    PostgresJdbcTable<PostgresJdbcTestTable>,
    TestTable<PostgresJdbcTestTable, ResultSet, PreparedStatement> {
    override val tableName: String = "test"
    override val a = text("a")
    override val b = text("b")
    val c = uuidNullable("c")
    val d = timestamp("d")
    val e = doublePrecision("e")
}
