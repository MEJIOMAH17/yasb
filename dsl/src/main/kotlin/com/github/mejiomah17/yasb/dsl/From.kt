package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class From(
    val select: Select,
    val source: SelectionSource
) : FromClauseAndSelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val builtExpressions = select.expressions.map { it.build() }
        val selectionPart = builtExpressions.map { it.sqlDefinition }.joinToString(", ")
        return QueryForExecute(
            sqlDefinition = "SELECT $selectionPart FROM ${source.sqlDefinition}",
            returnExpressions = select.expressions,
            parameters = builtExpressions.flatMap { it.parameters } + source.parameters
        )
    }
}

fun Select.from(source: SelectionSource): From {
    return From(this, source)
}