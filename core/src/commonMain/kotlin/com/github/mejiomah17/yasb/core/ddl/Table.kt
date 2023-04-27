package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.kotlin.concurrent.collections.ThreadSafeMap
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface Table<TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> : SelectionSource<DRIVER_DATA_SOURCE> {
    val tableName: String
    override val sqlDefinition: String get() = tableName
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>> get() = emptyList()

    fun allColumns(): List<Column<TABLE, *, *>> {
        return tableToColumns[this] as List<Column<TABLE, *, *>>
    }

    fun <V> register(column: Column<TABLE, V, DRIVER_DATA_SOURCE>): Column<TABLE, V, DRIVER_DATA_SOURCE> {
        tableToColumns.computeIfAbsent(this) {
            mutableListOf()
        }.add(column)
        return column
    }

    fun <V> registerNullable(column: Column<TABLE, V, DRIVER_DATA_SOURCE>): Column<TABLE, V?, DRIVER_DATA_SOURCE> {
        return register(column) as Column<TABLE, V?, DRIVER_DATA_SOURCE>
    }
}

private val tableToColumns = ThreadSafeMap<Table<*, *>, MutableList<Column<*, *, *>>>()
