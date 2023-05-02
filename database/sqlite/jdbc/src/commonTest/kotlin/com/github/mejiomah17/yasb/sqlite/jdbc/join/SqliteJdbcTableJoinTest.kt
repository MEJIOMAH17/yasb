package com.github.mejiomah17.yasb.sqlite.jdbc.join

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yasb.sqlite.SqliteTableJoinTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcTableJoinTest :
    SqliteTableJoinTest<SqliteJdbcTest.SecondTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>,
    SqliteJdbcTest()
