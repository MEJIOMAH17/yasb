package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun sql(): String
    fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
}
