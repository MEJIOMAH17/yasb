package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface Table<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val tableName: String
    override fun sql(): String {
        return tableName
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return emptyList()
    }

    fun allColumns(): List<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return tableToColumns[this] as List<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
    }

    fun <V> register(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        tableToColumns.computeIfAbsent(this) {
            mutableListOf()
        }.add(column)
        return column
    }

    fun <V> registerNullable(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): Column<TABLE, V?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return register(column) as Column<TABLE, V?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    }
}

private val tableToColumns: MutableMap<Table<*, *, *>, MutableList<Column<*, *, *, *>>> =
    co.touchlab.stately.collections.SharedHashMap()
