package com.github.mejiomah17.yasb.postgres.jdbc.transaction

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionTest
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTestTable
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresTest

class PostgresJdbcTransactionTest : PostgresTest(), JdbcTransactionTest<PostgresJdbcTestTable>
