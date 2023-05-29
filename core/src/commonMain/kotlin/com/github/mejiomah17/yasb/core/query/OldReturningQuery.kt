package com.github.mejiomah17.yasb.core.query

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
}

@Deprecated("ReturningQuery")
class OldReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val sql: String,
    val parameters: List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
    val returnExpressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun sql(): String {
        return sql
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return parameters
    }
}
