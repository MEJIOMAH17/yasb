package com.github.mejiomah17.yaksb.core.jdbc.transaction

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.dsl.TransactionTest
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class JdbcTransactionFactoryTest<
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : TransactionTest<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION>
