package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.sqlite.SqliteInsertTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcInsertTest :
    SqliteInsertTest<SqliteJdbcTestTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest()
