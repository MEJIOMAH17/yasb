package com.github.mejiomah17.yaksb.sqlite

import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.dsl.OrderByTest
import com.github.mejiomah17.yaksb.dsl.TestTable

interface SqliteOrderByTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    OrderByTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test",
            """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |3,
                    |false
                    | )
            """.trimMargin(),
            """ INSERT INTO test (a,b,c,d) values (
                    '42',
                    '42',
                    4,
                    true
                    )
            """.trimIndent()
        )
    }
}
