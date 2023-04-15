package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.kotlin.concurrent.collections.ThreadSafeMap
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface Table<T : Table<T, S>, S> : SelectionSource<S> {
    val tableName: String
    override val sqlDefinition: String get() = tableName
    override val parameters: List<Parameter<*, S>> get() = emptyList()

    fun allColumns(): List<Column<T, *, *>> {
        return tableToColumns[this] as List<Column<T, *, *>>
    }

    fun <V> register(column: Column<T, V, S>): Column<T, V, S> {
        tableToColumns.computeIfAbsent(this) {
            mutableListOf()
        }.add(column)
        return column
    }

    fun <V> registerNullable(column: Column<T, V, S>): Column<T, V?, S> {
        return register(column) as Column<T, V?, S>
    }
}

private val tableToColumns = ThreadSafeMap<Table<*, *>, MutableList<Column<*, *, *>>>()
