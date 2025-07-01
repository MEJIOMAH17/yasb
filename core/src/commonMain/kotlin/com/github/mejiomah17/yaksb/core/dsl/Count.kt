package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

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

context (dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> count(expression: Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): Count<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return Count(expression, dialect.longType())
}
