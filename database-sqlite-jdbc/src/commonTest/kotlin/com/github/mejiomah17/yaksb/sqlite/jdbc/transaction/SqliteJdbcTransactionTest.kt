package com.github.mejiomah17.yaksb.sqlite.jdbc.transaction

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionTest
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcTest
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcTestTable

class SqliteJdbcTransactionTest :
    SqliteJdbcTest(),
    JdbcTransactionTest<SqliteJdbcTestTable>
