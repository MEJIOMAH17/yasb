package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val select: Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val builtExpressions = select.expressions.map { it.build() }
        val selectionPart = builtExpressions.map { it.sqlDefinition }.joinToString(", ")
        return QueryForExecute(
            sqlDefinition = "SELECT $selectionPart FROM ${source.sqlDefinition}",
            returnExpressions = select.expressions,
            parameters = builtExpressions.flatMap { it.parameters } + source.parameters
        )
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.from(source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return From(this, source)
}
