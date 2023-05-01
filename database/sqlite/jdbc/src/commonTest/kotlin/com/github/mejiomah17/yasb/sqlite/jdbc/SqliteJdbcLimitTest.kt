package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.sqlite.SqliteLimitTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcLimitTest :
    SqliteLimitTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteJdbcTest()
