@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Insert<T : Table<T, S>, S> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *, S>, List<Any?>>
) {
    private val size: Int = columnsToValues.values.first().size

    init {
        require(columnsToValues.values.all { it.size == size }) {
            "All columns should have same amount of values to insert"
        }
    }

    fun buildInsertQuery(): QueryPart<S> {
        val columns = columnsToValues.keys.joinToString(",") { it.name }
        val valuesSql = StringBuilder()
        val parameters = mutableListOf<Parameter<*, S>>()
        for (i in 0 until size) {
            val rowInSql = columnsToValues.map { (column, values) ->
                column as Column<T, Any, S>
                val value = values[i]
                if (value is DefaultQueryPart) {
                    value.sqlDefinition
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

class InsertWithReturn<T : Table<T, S>, S> internal constructor(
    private val insert: Insert<T, S>,
    private val returning: Returning<S>
) {
    fun buildInsertQuery(): QueryForExecute<S> {
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
fun <T : Table<T, S>, S, E> insertInto(
    table: T,
    source: Iterable<E>,
    block: T.(InsertContext<T, S>, E) -> Unit
): Insert<T, S> {
    val columns = mutableMapOf<Column<T, *, S>, MutableList<Any?>>()
    source.forEachIndexed { i, e ->
        val insertContext = InsertContext<T, S>()
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

fun <T : Table<T, S>, S> insertInto(
    table: T,
    block: T.(InsertContext<T, S>) -> Unit
): Insert<T, S> {
    val columns = mutableMapOf<Column<T, *, S>, List<Any?>>()
    val insertContext = InsertContext<T, S>()
    block(table, insertContext)
    insertContext.columns.forEach { (column, value) ->
        columns[column] = listOf(value)
    }
    return Insert(table, columns)
}

fun <T : Table<T, S>, S> insertInto(
    table: T,
    returning: Returning<S>,
    block: T.(InsertContext<T, S>) -> Unit
): InsertWithReturn<T, S> {
    return InsertWithReturn(insertInto(table, block), returning)
}

/**
 * SupportInsertWithDefaultValue - [block] could skip column initialization.
 * Yasb asks database use default value for skipped columns. Consequently, database should support default values
 */
context(SupportsInsertWithDefaultValue)
fun <T : Table<T, S>, S, E> insertInto(
    table: T,
    returning: Returning<S>,
    source: Iterable<E>,
    block: T.(InsertContext<T, S>, E) -> Unit
): InsertWithReturn<T, S> {
    return InsertWithReturn(insertInto(table, source, block), returning)
}

class InsertContext<T : Table<T, S>, S> {
    internal val columns = mutableMapOf<Column<T, *, S>, Any?>()
    operator fun <V> set(column: Column<T, V, S>, value: V) {
        columns[column] = value
    }
}
