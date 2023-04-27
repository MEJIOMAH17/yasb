package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Select<S>(
    val expressions: List<Expression<*, S>>
) {
    init {
        require(expressions.isNotEmpty()) {
            "Select query should have at least 1 arg"
        }
    }
}

fun <S> select(vararg expressions: Expression<*, S>): Select<S> {
    return Select(expressions.toList())
}

fun <S> select(expressions: List<Expression<*, S>>): Select<S> {
    return Select(expressions)
}
