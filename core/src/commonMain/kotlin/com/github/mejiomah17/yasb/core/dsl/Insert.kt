@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Insert<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *, DRIVER_DATA_SOURCE>, List<Any?>>
) {
    private val size: Int = columnsToValues.values.first().size

    init {
        require(columnsToValues.values.all { it.size == size }) {
            "All columns should have same amount of values to insert"
        }
    }

    fun buildInsertQuery(): QueryPart<DRIVER_DATA_SOURCE> {
        val columns = columnsToValues.keys.joinToString(",") { it.name }
        val valuesSql = StringBuilder()
        val parameters = mutableListOf<Parameter<*, DRIVER_DATA_SOURCE>>()
        for (i in 0 until size) {
            val rowInSql = columnsToValues.map { (column, values) ->
                column as Column<T, Any, DRIVER_DATA_SOURCE>
                val value = values[i]
                if (value is DefaultQueryPart) {
                    DefaultQueryPart.sqlDefinition
                } else {
                    val parameter = column.databaseType.parameterFactory().invoke(value)
                    parameters.add(parameter)
                    parameter.parameterInSql
                }
            }.joinToString(",")
            valuesSql.append("(")
                .append(rowInSql)
                .append(")")
            if (i != size - 1) {
                valuesSql.appendLine(",")
            }
        }

        return QueryPartImpl(
            sqlDefinition = "INSERT INTO ${table.tableName} ($columns) VALUES " + valuesSql,
            parameters = parameters
        )
    }
}

class InsertWithReturn<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> internal constructor(
    private val insert: Insert<T, DRIVER_DATA_SOURCE>,
    private val returning: Returning<DRIVER_DATA_SOURCE>
) {
    fun buildInsertQuery(): QueryForExecute<DRIVER_DATA_SOURCE> {
        val query = insert.buildInsertQuery()
        val returnExpressions = returning.expressions.map { it.build() }
        return QueryForExecute(
            sqlDefinition = query.sqlDefinition + "RETURNING ${returnExpressions.joinToString(", ") { it.sqlDefinition }}",
            parameters = query.parameters + returnExpressions.flatMap { it.parameters },
            returnExpressions = returning.expressions
        )
    }
}

/**
 * SupportInsertWithDefaultValue - [block] could skip column initialization.
 * Yasb asks database use default value for skipped columns. Consequently, database should support default values
 */
context(SupportsInsertWithDefaultValue)
fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE, E> insertInto(
    table: T,
    source: Iterable<E>,
    block: T.(InsertContext<T, DRIVER_DATA_SOURCE>, E) -> Unit
): Insert<T, DRIVER_DATA_SOURCE> {
    val columns = mutableMapOf<Column<T, *, DRIVER_DATA_SOURCE>, MutableList<Any?>>()
    source.forEachIndexed { i, e ->
        val insertContext = InsertContext<T, DRIVER_DATA_SOURCE>()
        block(table, insertContext, e)
        insertContext.columns.forEach { (column, value) ->
            // iterate over this feeling
            val list = columns.computeIfAbsent(column) {
                val list = ArrayList<Any?>(i)
                (0 until i).forEach { list.add(DefaultQueryPart) }
                list
            }
            list.add(value)
        }
        columns.filterValues { it.size < i + 1 }.forEach { _, v ->
            v.add(DefaultQueryPart)
        }
    }
    return Insert(table, columns)
}

fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> insertInto(
    table: T,
    block: T.(InsertContext<T, DRIVER_DATA_SOURCE>) -> Unit
): Insert<T, DRIVER_DATA_SOURCE> {
    val columns = mutableMapOf<Column<T, *, DRIVER_DATA_SOURCE>, List<Any?>>()
    val insertContext = InsertContext<T, DRIVER_DATA_SOURCE>()
    block(table, insertContext)
    insertContext.columns.forEach { (column, value) ->
        columns[column] = listOf(value)
    }
    return Insert(table, columns)
}

fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> insertInto(
    table: T,
    returning: Returning<DRIVER_DATA_SOURCE>,
    block: T.(InsertContext<T, DRIVER_DATA_SOURCE>) -> Unit
): InsertWithReturn<T, DRIVER_DATA_SOURCE> {
    return InsertWithReturn(insertInto(table, block), returning)
}

/**
 * SupportInsertWithDefaultValue - [block] could skip column initialization.
 * Yasb asks database use default value for skipped columns. Consequently, database should support default values
 */
context(SupportsInsertWithDefaultValue)
fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE, E> insertInto(
    table: T,
    returning: Returning<DRIVER_DATA_SOURCE>,
    source: Iterable<E>,
    block: T.(InsertContext<T, DRIVER_DATA_SOURCE>, E) -> Unit
): InsertWithReturn<T, DRIVER_DATA_SOURCE> {
    return InsertWithReturn(insertInto(table, source, block), returning)
}

class InsertContext<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> {
    internal val columns = mutableMapOf<Column<T, *, DRIVER_DATA_SOURCE>, Any?>()
    operator fun <V> set(column: Column<T, V, DRIVER_DATA_SOURCE>, value: V) {
        columns[column] = value
    }
}
