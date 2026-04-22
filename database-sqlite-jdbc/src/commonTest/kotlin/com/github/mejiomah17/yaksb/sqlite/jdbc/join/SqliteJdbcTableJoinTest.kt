package com.github.mejiomah17.yaksb.sqlite.jdbc.join

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.SqliteTableJoinTest
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteJdbcTableJoinTest :
    SqliteJdbcTest(),
    SqliteTableJoinTest<SqliteJdbcTest.SecondTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>
