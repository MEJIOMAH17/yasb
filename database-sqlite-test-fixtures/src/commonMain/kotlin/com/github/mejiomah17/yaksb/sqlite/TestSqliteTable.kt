package com.github.mejiomah17.yaksb.sqlite

import com.github.mejiomah17.yaksb.core.ddl.Column
import com.github.mejiomah17.yaksb.dsl.TestTable

interface TestSqliteTable<T : TestTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TestTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val c: Column<T, Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val d: Column<T, Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val e: Column<T, ByteArray, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
