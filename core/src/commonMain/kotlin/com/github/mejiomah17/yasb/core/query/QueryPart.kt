package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface QueryPart<S> {
    val sqlDefinition: String
    val parameters: List<Parameter<*, S>>
}

class QueryPartImpl<S>(override val sqlDefinition: String, override val parameters: List<Parameter<*, S>>) :
    QueryPart<S>
