@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.SupportsInsertReturning
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.ReturningQuery

class Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val insert: Insert<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
) : ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return expressions
    }

    override fun sql(): String {
        return insert.sql() + " RETURNING ${expressions.joinToString(", ") { it.sql() }}"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return insert.parameters() + expressions.flatMap { it.parameters() }
    }
}

context(SupportsInsertReturning)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Insert<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.returning(
    expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Returning(this, expressions)
}

context(SupportsInsertReturning)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Insert<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.returning(
    vararg expressions: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Returning(this, expressions.toList())
}
