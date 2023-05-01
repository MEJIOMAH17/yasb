package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface TestTable<
    T : TestTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT
    > : Table<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val a: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val b: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
