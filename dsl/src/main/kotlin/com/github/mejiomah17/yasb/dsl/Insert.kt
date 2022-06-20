package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Insert<T : Table<T>> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *>, List<Any?>>
) {
    private val size: Int = columnsToValues.values.first().size

    init {
        require(columnsToValues.values.all { it.size == size }) {
            "All columns should have same amount of values to insert"
        }
    }

    fun buildInsertQuery(): QueryPart {
        val columns = columnsToValues.keys.joinToString(",") { it.name }
        val valuesSql = StringBuilder()
        val parameters = mutableListOf<Parameter<*>>()
        for (i in 0 until size) {
            val rowInSql = columnsToValues.map { (column, values) ->
                column as Column<T, Any>
                val value = values[i]
                if (value is DefaultQueryPart) {
                    value.sqlDefinition
                } else {
                    val parameter = column.databaseType.parameterFactory().invoke(value)
                    parameters.add(parameter)
                    parameter.parameterInJdbcQuery
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

class InsertWithReturn<T : Table<T>> internal constructor(
    private val insert: Insert<T>,
    private val returning: Returning,
) {
    fun buildInsertQuery(): QueryForExecute {
        val query = insert.buildInsertQuery()
        val returnExpressions = returning.expressions.map { it.build() }
        return QueryForExecute(
            sqlDefinition = query.sqlDefinition + "RETURNING ${returnExpressions.joinToString(", ") { it.sqlDefinition }}",
            parameters = query.parameters + returnExpressions.flatMap { it.parameters },
            returnExpressions = returning.expressions
        )
    }
}


fun <T : Table<T>, E> insertInto(
    table: T,
    source: Iterable<E>,
    block: T.(InsertContext<T>, E) -> Unit
): Insert<T> {
    val columns = mutableMapOf<Column<T, *>, MutableList<Any?>>()
    source.forEachIndexed { i, e ->
        val insertContext = InsertContext<T>()
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

fun <T : Table<T>> insertInto(
    table: T,
    block: T.(InsertContext<T>) -> Unit
): Insert<T> {
    return insertInto(table, listOf(1)) { context, _ ->
        block(context)
    }
}

fun <T : Table<T>> insertInto(
    table: T,
    returning: Returning,
    block: T.(InsertContext<T>) -> Unit
): InsertWithReturn<T> {
    return InsertWithReturn(insertInto(table, block), returning)
}

fun <T : Table<T>, E> insertInto(
    table: T,
    returning: Returning,
    source: Iterable<E>,
    block: T.(InsertContext<T>, E) -> Unit
): InsertWithReturn<T> {
    return InsertWithReturn(insertInto(table, source, block), returning)
}

class InsertContext<T : Table<T>> {
    internal val columns = mutableMapOf<Column<T, *>, Any?>()
    operator fun <V> set(column: Column<T, V>, value: V) {
        columns[column] = value
    }
}

internal class ColumnToValue<T : Table<T>, V>(
    val column: Column<T, V>,
    val value: V?
)


