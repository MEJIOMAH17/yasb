package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.expression.Expression

class Returning(
    val expressions: List<Expression<*>>
) {
    constructor(vararg expressions: Expression<*>) : this(expressions.toList())
}