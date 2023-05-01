package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.dsl.FromTest
import com.github.mejiomah17.yasb.dsl.TestTable

interface SqliteFromTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    FromTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test",
            """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | )
            """.trimMargin()
        )
    }
}
