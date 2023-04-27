package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory

interface SelectionTest<TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE, DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE>> {

    abstract fun columnA(): Column<TABLE, String, DRIVER_DATA_SOURCE>
    abstract fun columnB(): Column<TABLE, String, DRIVER_DATA_SOURCE>
    abstract fun parameter(): Parameter<String, DRIVER_DATA_SOURCE>
    abstract fun tableTest(): TABLE
    abstract fun transactionFactory(): TransactionFactory<DIALECT, DRIVER_DATA_SOURCE>
    val databaseDialect: DIALECT get() = transactionFactory().dialect()
}
