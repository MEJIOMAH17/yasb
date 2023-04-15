package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory

interface SelectionTest<T : Table<T, S>, S, D : DatabaseDialect<S>> {

    abstract fun columnA(): Column<T, String, S>
    abstract fun columnB(): Column<T, String, S>
    abstract fun parameter(): Parameter<String, S>
    abstract fun tableTest(): T
    abstract fun transactionFactory(): TransactionFactory<D, S>
    val databaseDialect: D get() = transactionFactory().dialect()
}
