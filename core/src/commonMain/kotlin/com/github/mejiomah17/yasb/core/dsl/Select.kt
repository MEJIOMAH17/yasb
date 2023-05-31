package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) : SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    init {
        require(expressions.isNotEmpty()) {
            "Select query should have at least 1 arg"
        }
    }

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return expressions
    }

    override fun sql(): String {
        return "SELECT ${expressions.map { it.sql() }.joinToString(", ")}"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return expressions.flatMap { it.parameters() }
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> select(vararg expressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Select(expressions.toList())
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> select(expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>): Select<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Select(expressions)
}
