package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Returning<S>(
    val expressions: List<Expression<*, S>>
) {
    constructor(vararg expressions: Expression<*, S>) : this(expressions.toList())
}
