package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class QueryForExecute<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    override val sqlDefinition: String,
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
    val returnExpressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) : QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
