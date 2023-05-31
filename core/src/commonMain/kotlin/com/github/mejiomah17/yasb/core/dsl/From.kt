package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val select: Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return select.returnExpressions()
    }

    override fun sql(): String {
        return "${select.sql()} FROM ${source.sql()}"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return select.parameters() + source.parameters()
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.from(source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return From(this, source)
}
