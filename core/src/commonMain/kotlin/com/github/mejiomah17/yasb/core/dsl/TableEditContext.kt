package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface TableEditContext<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    operator fun <V> set(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, value: V)
}
