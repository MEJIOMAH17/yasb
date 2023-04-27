package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface QueryPart<DRIVER_DATA_SOURCE> {
    val sqlDefinition: String
    val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>>
}

class QueryPartImpl<DRIVER_DATA_SOURCE>(
    override val sqlDefinition: String,
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>>
) :
    QueryPart<DRIVER_DATA_SOURCE>
