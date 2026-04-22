package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.SelectionSource
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

internal class DeleteFrom<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val delete: Delete,
    private val source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : DeleteFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun sql(): String = "${delete.sql()} FROM ${source.sql()}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = source.parameters()
}

internal class SelectFrom<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val select: Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = select.returnExpressions()

    override fun sql(): String = "${select.sql()} FROM ${source.sql()}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = select.parameters() + source.parameters()
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.from(
    source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = SelectFrom(this, source)

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Delete.from(
    source: SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): DeleteFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = DeleteFrom(this, source)
