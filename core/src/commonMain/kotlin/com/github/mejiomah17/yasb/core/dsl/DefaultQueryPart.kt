package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart

internal object DefaultQueryPart : QueryPart<Any, Any> {
    override fun sql(): String = "DEFAULT"
    override fun parameters(): List<Parameter<*, Any, Any>> = emptyList()
}
