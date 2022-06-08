package com.github.mejiomah17.yasb.core.ddl

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

interface Table<T : Table<T>> {
    val tableName: String
    fun allColumns(): List<Column<T, *>> {
        return tableToColumns[this] as List<Column<T, *>>
    }

    fun <Tb : Table<T>, V> register(column: Column<Tb, V>): Column<T, V> {
        tableToColumns.computeIfAbsent(this) {
            CopyOnWriteArrayList()
        }.add(column)
        return column as Column<T, V>
    }

    fun <Tb : Table<T>, V> registerNullable(column: Column<Tb, V>): Column<T, V?> {
        return register(column) as Column<T, V?>
    }
}

private val tableToColumns = ConcurrentHashMap<Table<*>, MutableList<Column<*, *>>>()



