package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

// TODO test
class Update<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val table: TABLE,
    private val columnsToValues: Map<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, Any?>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>?
) {
    fun buildUpdateQuery(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val parameters = mutableListOf<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>()
        val setPart = columnsToValues.map { (column, value) ->
            column as Column<TABLE, Any, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
            val sqlValue = if (value is DefaultQueryPart) {
                DefaultQueryPart.sqlDefinition
            } else {
                val parameter = column.databaseType.parameterFactory().invoke(value)
                parameters.add(parameter)
                parameter.parameterInSql
            }
            "${column.name} = $sqlValue"
        }.joinToString(", ")

        val wherePart = where?.build()
        val whereSql = wherePart?.let { "WHERE ${it.sqlDefinition}" } ?: ""
        return QueryPartImpl(
            sqlDefinition = "UPDATE ${table.tableName} SET $setPart $whereSql",
            parameters = parameters + (wherePart?.parameters ?: emptyList())
        )
    }
}

class UpdateContext<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    internal val columns = mutableMapOf<Column<TABLE, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, Any?>()
    operator fun <V> set(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, value: V) {
        columns[column] = value
    }
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> update(
    table: TABLE,
    set: TABLE.(UpdateContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) -> Unit,
    where: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): Update<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val context = UpdateContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>()
    table.set(context)
    return Update(table, context.columns, where(ConditionContext))
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> update(
    table: TABLE,
    set: TABLE.(UpdateContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) -> Unit
): Update<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val context = UpdateContext<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>()
    table.set(context)
    return Update(table, context.columns, null)
}
