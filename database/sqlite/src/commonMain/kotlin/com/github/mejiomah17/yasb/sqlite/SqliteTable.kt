package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface SqliteTable<T : SqliteTable<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> : Table<T, DRIVER_DATA_SOURCE> {
    fun text(name: String): Column<T, String, DRIVER_DATA_SOURCE>

    fun textNullable(name: String): Column<T, String?, DRIVER_DATA_SOURCE>

    fun bool(name: String): Column<T, Boolean, DRIVER_DATA_SOURCE>

    fun boolNullable(name: String): Column<T, Boolean?, DRIVER_DATA_SOURCE>

    fun long(name: String): Column<T, Long, DRIVER_DATA_SOURCE>
    fun longNullable(name: String): Column<T, Long?, DRIVER_DATA_SOURCE>
}
