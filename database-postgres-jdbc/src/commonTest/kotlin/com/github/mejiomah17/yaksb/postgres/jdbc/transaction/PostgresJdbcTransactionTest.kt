package com.github.mejiomah17.yaksb.postgres.jdbc.transaction

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionTest
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresJdbcTestTable
import com.github.mejiomah17.yaksb.postgres.jdbc.PostgresTest

class PostgresJdbcTransactionTest :
    PostgresTest(),
    JdbcTransactionTest<PostgresJdbcTestTable>
