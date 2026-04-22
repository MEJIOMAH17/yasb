package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.SqliteOrderByTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcOrderByTest :
    SqliteJdbcTest(),
    SqliteOrderByTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>
