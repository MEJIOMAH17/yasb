package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class QueryForExecute<S>(
    override val sqlDefinition: String,
    override val parameters: List<Parameter<*, S>>,
    val returnExpressions: List<Expression<*, S>>
) : QueryPart<S>
