package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.sqlite.SqliteTestTable
import java.sql.PreparedStatement
import java.sql.ResultSet

object SqliteJdbcTestTable :
    SqliteJdbcTable<SqliteJdbcTestTable>,
    SqliteTestTable<SqliteJdbcTestTable, ResultSet, PreparedStatement> {
    override val tableName: String = "test"
    override val a = text("a")
    override val b = text("b")
}
