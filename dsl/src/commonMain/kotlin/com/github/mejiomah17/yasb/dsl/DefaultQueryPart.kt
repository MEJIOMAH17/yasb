package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart

internal object DefaultQueryPart : QueryPart {
    override val sqlDefinition: String = "DEFAULT"
    override val parameters: List<Parameter<*>> = emptyList()
}
