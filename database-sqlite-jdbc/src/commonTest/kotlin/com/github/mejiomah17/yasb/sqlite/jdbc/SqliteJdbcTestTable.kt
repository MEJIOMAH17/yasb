package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.sqlite.TestSqliteTable
import java.sql.PreparedStatement
import java.sql.ResultSet

object SqliteJdbcTestTable :
    SqliteJdbcTable<SqliteJdbcTestTable>,
    TestSqliteTable<SqliteJdbcTestTable, ResultSet, PreparedStatement> {
    override val tableName: String = "test"
    override val a = text("a")
    override val b = text("b")
    override val c = long("c")
    override val d = bool("d")
    override val e = blob("e")
}
