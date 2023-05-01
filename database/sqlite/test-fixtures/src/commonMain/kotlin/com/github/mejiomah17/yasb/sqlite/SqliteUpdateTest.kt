package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.dsl.TestTable
import com.github.mejiomah17.yasb.dsl.UpdateTest

interface SqliteUpdateTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    UpdateTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test"
        )
    }
}