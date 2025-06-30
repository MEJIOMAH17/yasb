package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.sqlite.SqliteLimitTest
import com.github.mejiomah17.yasb.sqlite.SqliteOrderByTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcOrderByTest :
    SqliteOrderByTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest()
