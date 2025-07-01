package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.SqliteUpdateTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcUpdateTest :
    SqliteUpdateTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest()
