@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class Count<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val expression: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val databaseType: DatabaseType<Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : AliasableExpressionForCondition<Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun databaseType(): DatabaseType<Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return databaseType
    }

    override fun sql(): String {
        return "COUNT(${expression.sql()})"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return expression.parameters()
    }
}

context (DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> count(expression: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): Count<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Count(expression, longType())
}
