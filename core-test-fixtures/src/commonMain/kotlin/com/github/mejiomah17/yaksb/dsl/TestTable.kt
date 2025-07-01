package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.ddl.Column
import com.github.mejiomah17.yaksb.core.ddl.Table

interface TestTable<
    T : TestTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    > : Table<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val a: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val b: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
