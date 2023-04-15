package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

// TODO test
class Update<T : Table<T, S>, S> internal constructor(
    private val table: T,
    private val columnsToValues: Map<Column<T, *, S>, Any?>,
    private val where: Expression<Boolean, S>?
) {
    fun buildUpdateQuery(): QueryPart<S> {
        val parameters = mutableListOf<Parameter<*, S>>()
        val setPart = columnsToValues.map { (column, value) ->
            column as Column<T, Any, S>
            val sqlValue = if (value is DefaultQueryPart) {
                value.sqlDefinition
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

class UpdateContext<T : Table<T, S>, S> {
    internal val columns = mutableMapOf<Column<T, *, S>, Any?>()
    operator fun <V> set(column: Column<T, V, S>, value: V) {
        columns[column] = value
    }
}

fun <T : Table<T, S>, S> update(
    table: T,
    set: T.(UpdateContext<T, S>) -> Unit,
    where: ConditionContext.() -> Expression<Boolean, S>
): Update<T, S> {
    val context = UpdateContext<T, S>()
    table.set(context)
    return Update(table, context.columns, where(ConditionContext))
}

fun <T : Table<T, S>, S> update(
    table: T,
    set: T.(UpdateContext<T, S>) -> Unit
): Update<T, S> {
    val context = UpdateContext<T, S>()
    table.set(context)
    return Update(table, context.columns, null)
}
