package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class From<S>(
    val select: Select<S>,
    val source: SelectionSource<S>
) : FromClauseAndSelectQuery<S> {
    override fun buildSelectQuery(): QueryForExecute<S> {
        val builtExpressions = select.expressions.map { it.build() }
        val selectionPart = builtExpressions.map { it.sqlDefinition }.joinToString(", ")
        return QueryForExecute(
            sqlDefinition = "SELECT $selectionPart FROM ${source.sqlDefinition}",
            returnExpressions = select.expressions,
            parameters = builtExpressions.flatMap { it.parameters } + source.parameters
        )
    }
}

fun <S> Select<S>.from(source: SelectionSource<S>): From<S> {
    return From(this, source)
}
