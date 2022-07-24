package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

//TODO test
class Update<T : Table<T>> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *>, Any?>,
    private val where: Expression<Boolean>?,
) {
    fun buildUpdateQuery(): QueryPart {
        val parameters = mutableListOf<Parameter<*>>()
        val setPart = columnsToValues.map { (column, value) ->
            column as Column<T, Any>
            val sqlValue = if (value is DefaultQueryPart) {
                value.sqlDefinition
            } else {
                val parameter = column.databaseType.parameterFactory().invoke(value)
                parameters.add(parameter)
                parameter.parameterInJdbcQuery
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

class UpdateContext<T : Table<T>> {
    internal val columns = mutableMapOf<Column<T, *>, Any?>()
    operator fun <V> set(column: Column<T, V>, value: V) {
        columns[column] = value
    }
}

fun <T : Table<T>> update(
    table: T,
    set: T.(UpdateContext<T>) -> Unit,
    where: ConditionContext.() -> Expression<Boolean>
): Update<T> {
    val context = UpdateContext<T>()
    table.set(context)
    return Update(table, context.columns, where(ConditionContext))
}

fun <T : Table<T>> update(
    table: T,
    set: T.(UpdateContext<T>) -> Unit,
): Update<T> {
    val context = UpdateContext<T>()
    table.set(context)
    return Update(table, context.columns, null)
}