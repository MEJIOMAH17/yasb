package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

// TODO test
class Update<TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> internal constructor(
    private val table: TABLE,
    private val columnsToValues: Map<Column<TABLE, *, DRIVER_DATA_SOURCE>, Any?>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE>?
) {
    fun buildUpdateQuery(): QueryPart<DRIVER_DATA_SOURCE> {
        val parameters = mutableListOf<Parameter<*, DRIVER_DATA_SOURCE>>()
        val setPart = columnsToValues.map { (column, value) ->
            column as Column<TABLE, Any, DRIVER_DATA_SOURCE>
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

class UpdateContext<TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> {
    internal val columns = mutableMapOf<Column<TABLE, *, DRIVER_DATA_SOURCE>, Any?>()
    operator fun <V> set(column: Column<TABLE, V, DRIVER_DATA_SOURCE>, value: V) {
        columns[column] = value
    }
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> update(
    table: TABLE,
    set: TABLE.(UpdateContext<TABLE, DRIVER_DATA_SOURCE>) -> Unit,
    where: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE>
): Update<TABLE, DRIVER_DATA_SOURCE> {
    val context = UpdateContext<TABLE, DRIVER_DATA_SOURCE>()
    table.set(context)
    return Update(table, context.columns, where(ConditionContext))
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> update(
    table: TABLE,
    set: TABLE.(UpdateContext<TABLE, DRIVER_DATA_SOURCE>) -> Unit
): Update<TABLE, DRIVER_DATA_SOURCE> {
    val context = UpdateContext<TABLE, DRIVER_DATA_SOURCE>()
    table.set(context)
    return Update(table, context.columns, null)
}
