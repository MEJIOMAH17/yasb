package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface QueryPart {
    val sqlDefinition: String
    val parameters: List<Parameter<*>>
}

class QueryPartImpl(override val sqlDefinition: String, override val parameters: List<Parameter<*>>) : QueryPart
