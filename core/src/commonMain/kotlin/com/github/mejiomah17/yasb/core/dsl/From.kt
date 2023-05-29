package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val select: Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun buildSelectQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val selectionPart = select.expressions.map { it.sql() }.joinToString(", ")
        return ReturningQuery(
            sql = "SELECT $selectionPart FROM ${source.sql()}",
            returnExpressions = select.expressions,
            parameters = select.expressions.flatMap { it.parameters() } + source.parameters()
        )
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.from(source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return From(this, source)
}
