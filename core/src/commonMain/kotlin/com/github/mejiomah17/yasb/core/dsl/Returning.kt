package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Returning<DRIVER_DATA_SOURCE>(
    val expressions: List<Expression<*, DRIVER_DATA_SOURCE>>
) {
    constructor(vararg expressions: Expression<*, DRIVER_DATA_SOURCE>) : this(expressions.toList())
}
