package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.SqliteGroupByTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcGroupByTest :
    SqliteGroupByTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest()
