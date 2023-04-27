package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

// TODO test
class Update<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *, DRIVER_DATA_SOURCE>, Any?>,
    private val where: Expression<Boolean, DRIVER_DATA_SOURCE>?
) {
    fun buildUpdateQuery(): QueryPart<DRIVER_DATA_SOURCE> {
        val parameters = mutableListOf<Parameter<*, DRIVER_DATA_SOURCE>>()
        val setPart = columnsToValues.map { (column, value) ->
            column as Column<T, Any, DRIVER_DATA_SOURCE>
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

class UpdateContext<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> {
    internal val columns = mutableMapOf<Column<T, *, DRIVER_DATA_SOURCE>, Any?>()
    operator fun <V> set(column: Column<T, V, DRIVER_DATA_SOURCE>, value: V) {
        columns[column] = value
    }
}

fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> update(
    table: T,
    set: T.(UpdateContext<T, DRIVER_DATA_SOURCE>) -> Unit,
    where: ConditionContext.() -> Expression<Boolean, DRIVER_DATA_SOURCE>
): Update<T, DRIVER_DATA_SOURCE> {
    val context = UpdateContext<T, DRIVER_DATA_SOURCE>()
    table.set(context)
    return Update(table, context.columns, where(ConditionContext))
}

fun <T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> update(
    table: T,
    set: T.(UpdateContext<T, DRIVER_DATA_SOURCE>) -> Unit
): Update<T, DRIVER_DATA_SOURCE> {
    val context = UpdateContext<T, DRIVER_DATA_SOURCE>()
    table.set(context)
    return Update(table, context.columns, null)
}
