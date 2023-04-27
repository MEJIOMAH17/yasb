package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Select<DRIVER_DATA_SOURCE>(
    val expressions: List<Expression<*, DRIVER_DATA_SOURCE>>
) {
    init {
        require(expressions.isNotEmpty()) {
            "Select query should have at least 1 arg"
        }
    }
}

fun <DRIVER_DATA_SOURCE> select(vararg expressions: Expression<*, DRIVER_DATA_SOURCE>): Select<DRIVER_DATA_SOURCE> {
    return Select(expressions.toList())
}

fun <DRIVER_DATA_SOURCE> select(expressions: List<Expression<*, DRIVER_DATA_SOURCE>>): Select<DRIVER_DATA_SOURCE> {
    return Select(expressions)
}
