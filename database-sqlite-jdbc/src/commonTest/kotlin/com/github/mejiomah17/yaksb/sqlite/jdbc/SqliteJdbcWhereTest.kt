package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.SqliteWhereTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcWhereTest :
    SqliteJdbcTest(),
    SqliteWhereTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>
