package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class QueryForExecute<DRIVER_DATA_SOURCE>(
    override val sqlDefinition: String,
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>>,
    val returnExpressions: List<Expression<*, DRIVER_DATA_SOURCE>>
) : QueryPart<DRIVER_DATA_SOURCE>
