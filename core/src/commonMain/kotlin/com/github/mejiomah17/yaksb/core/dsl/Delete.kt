package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.core.query.QueryPart

class Delete() : QueryPart<Any, Any> {

    override fun sql(): String {
        return "DELETE"
    }

    override fun parameters(): List<Parameter<*, Any, Any>> {
        return emptyList()
    }
}

fun delete(): Delete {
    return Delete()
}
