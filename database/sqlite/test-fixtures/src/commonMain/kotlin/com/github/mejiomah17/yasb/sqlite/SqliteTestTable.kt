package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column

interface SqliteTestTable<
    T : SqliteTestTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT
    > : SqliteTable<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val a: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val b: Column<T, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
