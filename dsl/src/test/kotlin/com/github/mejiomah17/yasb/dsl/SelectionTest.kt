package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import java.util.UUID

interface SelectionTest<T : Table<T>> {

    abstract fun columnA(): Column<T, String>
    abstract fun columnB(): Column<T, String>
    abstract fun parameter(): Parameter<String>
    abstract fun tableTest(): T
    abstract fun transactionFactory(): TransactionFactory
}