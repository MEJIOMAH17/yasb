package com.github.mejiomah17.yaksb.core.ddl

import co.touchlab.stately.collections.ConcurrentMutableMap
import com.github.mejiomah17.yaksb.core.SelectionSource
import com.github.mejiomah17.yaksb.core.parameter.Parameter

interface Table<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val tableName: String

    override fun sql(): String = tableName

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = emptyList()

    fun allColumns(): List<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> =
        tableToColumns[this] as List<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>

    fun <V> register(
        column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ): Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        tableToColumns
            .computeIfAbsent(this) {
                mutableListOf()
            }.add(column)
        return column
    }

    fun <V> registerNullable(
        column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ): Column<TABLE, V?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = register(column) as Column<TABLE, V?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}

private val tableToColumns: ConcurrentMutableMap<Table<*, *, *>, MutableList<Column<*, *, *, *>>> =
    co.touchlab.stately.collections
        .ConcurrentMutableMap()
