package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class From(
    val select: Select,
    val table: Table<*>
) : SelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val builtExpressions = select.expressions.map { it.build() }
        val selectionPart = builtExpressions.map { it.value }.joinToString(", ")
        return QueryForExecute(
            value = "SELECT $selectionPart FROM ${table.tableName}",
            returnExpressions = select.expressions,
            parameters = builtExpressions.flatMap { it.parameters }
        )
    }
}

fun Select.from(table: Table<*>): From {
    return From(this, table)
}