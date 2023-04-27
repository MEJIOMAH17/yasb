package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.kotlin.concurrent.collections.ThreadSafeMap
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface Table<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> : SelectionSource<DRIVER_DATA_SOURCE> {
    val tableName: String
    override val sqlDefinition: String get() = tableName
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>> get() = emptyList()

    fun allColumns(): List<Column<T, *, *>> {
        return tableToColumns[this] as List<Column<T, *, *>>
    }

    fun <V> register(column: Column<T, V, DRIVER_DATA_SOURCE>): Column<T, V, DRIVER_DATA_SOURCE> {
        tableToColumns.computeIfAbsent(this) {
            mutableListOf()
        }.add(column)
        return column
    }

    fun <V> registerNullable(column: Column<T, V, DRIVER_DATA_SOURCE>): Column<T, V?, DRIVER_DATA_SOURCE> {
        return register(column) as Column<T, V?, DRIVER_DATA_SOURCE>
    }
}

private val tableToColumns = ThreadSafeMap<Table<*, *>, MutableList<Column<*, *, *>>>()
