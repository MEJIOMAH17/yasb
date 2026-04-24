package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead

interface SelectionTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SqlTest {
    abstract fun parameter(): Parameter<String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    abstract fun tableTest(): TABLE

    fun <V> transaction(block: context(DIALECT) TRANSACTION.() -> V): V

    val databaseDialect: DIALECT
}
