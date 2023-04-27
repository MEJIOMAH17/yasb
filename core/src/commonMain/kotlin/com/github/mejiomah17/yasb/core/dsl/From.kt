package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class From<DRIVER_DATA_SOURCE>(
    val select: Select<DRIVER_DATA_SOURCE>,
    val source: SelectionSource<DRIVER_DATA_SOURCE>
) : FromClauseAndSelectQuery<DRIVER_DATA_SOURCE> {
    override fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE> {
        val builtExpressions = select.expressions.map { it.build() }
        val selectionPart = builtExpressions.map { it.sqlDefinition }.joinToString(", ")
        return QueryForExecute(
            sqlDefinition = "SELECT $selectionPart FROM ${source.sqlDefinition}",
            returnExpressions = select.expressions,
            parameters = builtExpressions.flatMap { it.parameters } + source.parameters
        )
    }
}

fun <DRIVER_DATA_SOURCE> Select<DRIVER_DATA_SOURCE>.from(source: SelectionSource<DRIVER_DATA_SOURCE>): From<DRIVER_DATA_SOURCE> {
    return From(this, source)
}
