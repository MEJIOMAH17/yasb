package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val sqlDefinition: String
    val parameters: List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
}

class QueryPartImpl<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    override val sqlDefinition: String,
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) :
    QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
