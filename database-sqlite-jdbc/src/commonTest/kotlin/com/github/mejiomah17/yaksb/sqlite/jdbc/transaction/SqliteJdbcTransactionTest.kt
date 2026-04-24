package com.github.mejiomah17.yaksb.sqlite.jdbc.transaction

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.dsl.TransactionTest
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcTest
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class SqliteJdbcTransactionTest :
    SqliteJdbcTest(),
    TransactionTest<ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead> {
    override fun exception(): Exception = SQLException()
}
