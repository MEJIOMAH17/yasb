package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

class Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
) : SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    init {
        require(expressions.isNotEmpty()) {
            "Select query should have at least 1 arg"
        }
    }

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = expressions

    override fun sql(): String = "SELECT ${expressions.joinToString(", ") { it.sql() }}"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = expressions.flatMap { it.parameters() }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> select(
    vararg expressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = Select(expressions.toList())

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> select(
    expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
): Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = Select(expressions)
