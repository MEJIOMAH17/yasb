package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Select(
    val expressions: List<Expression<*>>
) {
    init {
        require(expressions.isNotEmpty()) {
            "Select query should have at least 1 arg"
        }
    }
}

fun select(vararg expressions: Expression<*>): Select {
    return Select(expressions.toList())
}

fun select(expressions: List<Expression<*>>): Select {
    return Select(expressions)
}
