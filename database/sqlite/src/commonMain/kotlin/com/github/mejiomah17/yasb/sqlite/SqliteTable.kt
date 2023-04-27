package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface SqliteTable<TABLE : SqliteTable<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> :
    Table<TABLE, DRIVER_DATA_SOURCE> {
    fun text(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE>

    fun textNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE>

    fun bool(name: String): Column<TABLE, Boolean, DRIVER_DATA_SOURCE>

    fun boolNullable(name: String): Column<TABLE, Boolean?, DRIVER_DATA_SOURCE>

    fun long(name: String): Column<TABLE, Long, DRIVER_DATA_SOURCE>
    fun longNullable(name: String): Column<TABLE, Long?, DRIVER_DATA_SOURCE>
}
