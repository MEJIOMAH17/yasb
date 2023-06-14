package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface SqliteTable<TABLE : SqliteTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    fun text(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun textNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun bool(name: String): Column<TABLE, Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun boolNullable(name: String): Column<TABLE, Boolean?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun blob(name: String): Column<TABLE, ByteArray, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun blobNullable(name: String): Column<TABLE, ByteArray?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun long(name: String): Column<TABLE, Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun longNullable(name: String): Column<TABLE, Long?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
