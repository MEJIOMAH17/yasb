package com.github.mejiomah17.yasb.sqlite.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTestTable

class SqliteJdbcTransactionTest : SqliteJdbcTest(), JdbcTransactionTest<SqliteJdbcTestTable>
