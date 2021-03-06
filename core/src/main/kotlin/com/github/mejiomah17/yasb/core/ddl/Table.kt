package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.kotlin.concurrent.collections.ThreadSafeMap
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface Table<T : Table<T>> : SelectionSource {
    val tableName: String
    override val sqlDefinition: String get() = tableName
    override val parameters: List<Parameter<*>> get() = emptyList()

    fun allColumns(): List<Column<T, *>> {
        return tableToColumns[this] as List<Column<T, *>>
    }

    fun <Tb : Table<T>, V> register(column: Column<Tb, V>): Column<T, V> {
        tableToColumns.computeIfAbsent(this) {
            mutableListOf()
        }.add(column)
        return column as Column<T, V>
    }

    fun <Tb : Table<T>, V> registerNullable(column: Column<Tb, V>): Column<T, V?> {
        return register(column) as Column<T, V?>
    }
}

private val tableToColumns = ThreadSafeMap<Table<*>, MutableList<Column<*, *>>>()
