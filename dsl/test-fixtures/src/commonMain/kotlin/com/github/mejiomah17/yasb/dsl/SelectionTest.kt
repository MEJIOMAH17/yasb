package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory

interface SelectionTest<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE, D : DatabaseDialect<DRIVER_DATA_SOURCE>> {

    abstract fun columnA(): Column<T, String, DRIVER_DATA_SOURCE>
    abstract fun columnB(): Column<T, String, DRIVER_DATA_SOURCE>
    abstract fun parameter(): Parameter<String, DRIVER_DATA_SOURCE>
    abstract fun tableTest(): T
    abstract fun transactionFactory(): TransactionFactory<D, DRIVER_DATA_SOURCE>
    val databaseDialect: D get() = transactionFactory().dialect()
}
