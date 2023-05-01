package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.sqlite.SqliteFromTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcFromTest :
    SqliteFromTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest()
