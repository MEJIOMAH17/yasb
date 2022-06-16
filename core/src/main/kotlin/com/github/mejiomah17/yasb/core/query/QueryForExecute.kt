package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class QueryForExecute(
    override val value: String,
    override val parameters: List<Parameter<*>>,
    val returnExpressions: List<Expression<*>>
) : QueryPart