@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.Query

// TODO extract interface
class Insert<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val table: TABLE,
    private val columnsToValues: Map<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, List<Any?>>
) : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    private val size: Int = columnsToValues.values.first().size

    init {
        require(columnsToValues.values.all { it.size == size }) {
            "All columns should have same amount of values to insert"
        }
    }

    private val sqlToParams: Pair<String, List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>> by lazy {
        val columns = columnsToValues.keys.joinToString(",") { it.name }
        val valuesSql = StringBuilder()
        val parameters = mutableListOf<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>()
        for (i in 0 until size) {
            val rowInSql = columnsToValues.map { (column, values) ->
                column as Column<TABLE, Any, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
                val value = values[i]
                if (value is DefaultQueryPart) {
                    DefaultQueryPart.sql()
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
        "INSERT INTO ${table.tableName} ($columns) VALUES " + valuesSql to parameters
    }

    override fun sql(): String {
        return sqlToParams.first
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return sqlToParams.second
    }
}

/**
 * SupportInsertWithDefaultValue - [block] could skip column initialization.
 * Yasb asks database use default value for skipped columns. Consequently, database should support default values
 */
context(SupportsInsertWithDefaultValue)
fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, E> insertInto(
    table: TABLE,
    source: Iterable<E>,
    block: TABLE.(InsertContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, E) -> Unit
): Insert<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val columns = mutableMapOf<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, MutableList<Any?>>()
    source.forEachIndexed { i, e ->
        val insertContext = InsertContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>()
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

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> insertInto(
    table: TABLE,
    block: TABLE.(InsertContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) -> Unit
): Insert<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val columns = mutableMapOf<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, List<Any?>>()
    val insertContext = InsertContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>()
    block(table, insertContext)
    insertContext.columns.forEach { (column, value) ->
        columns[column] = listOf(value)
    }
    return Insert(table, columns)
}

class InsertContext<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TableEditContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    internal val columns = mutableMapOf<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, Any?>()
    override operator fun <V> set(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, value: V) {
        columns[column] = value
    }
}
