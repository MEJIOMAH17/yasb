package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

open class QueryWithParameters(
    value: String,
    val parameters: List<Parameter<*>>
) : DatabaseQuery(value) {
}