package com.github.mejiomah17.yaksb.sqlite

import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.dsl.InsertWithReturningTest
import com.github.mejiomah17.yaksb.dsl.TestTable

interface SqliteInsertTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : InsertWithReturningTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    override fun initSqlScripts(): List<String> =
        listOf(
            "DELETE from test",
        )
}
