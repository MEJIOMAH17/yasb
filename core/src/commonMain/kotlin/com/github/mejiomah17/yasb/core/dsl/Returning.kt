package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) {
    constructor(vararg expressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) : this(expressions.toList())
}
