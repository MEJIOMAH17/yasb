package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Insert<T : Table<T>> internal constructor(
    private val table: T,
    private val columnsToValues: List<ColumnToValue<T, *>>
) {
    fun buildInsertQuery(): QueryPart {
        val columns = columnsToValues.joinToString(",") { it.column.name }
        val parameters: List<Parameter<Any?>> = columnsToValues.map {
            val column = it.column as Column<T, Any?>
            column.databaseType.parameterFactory().invoke(it.value)
        }
        return QueryPartImpl(
            sqlDefinition = "INSERT INTO ${table.tableName} ($columns) VALUES " +
                    "(${parameters.joinToString(",") { it.parameterInJdbcQuery }})",
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

fun <T : Table<T>> insertInto(
    table: T,
    block: T.(InsertContext<T>) -> Unit
): Insert<T> {
    val insertContext = InsertContext<T>()
    block(table, insertContext)
    return Insert(table, insertContext.columns.getAll())
}

fun <T : Table<T>> insertInto(
    table: T,
    returning: Returning,
    block: T.(InsertContext<T>) -> Unit
): InsertWithReturn<T> {
    return InsertWithReturn(insertInto(table, block), returning)
}

class InsertContext<T : Table<T>> {
    internal val columns = ColumnsToValue<T>()
    operator fun <V> set(column: Column<T, V>, value: V) {
        columns[column] = value
    }
}

internal class ColumnToValue<T : Table<T>, V>(
    val column: Column<T, V>,
    val value: V?
)

internal class ColumnsToValue<T : Table<T>> {
    private val map = mutableMapOf<Column<T, *>, Any?>()
    operator fun <V> set(column: Column<T, V>, value: V) {
        map[column] = value
    }

    fun getAll(): List<ColumnToValue<T, *>> {
        return map.entries.map {
            ColumnToValue(it.key as Column<T, Any?>, it.value)
        }
    }
}


