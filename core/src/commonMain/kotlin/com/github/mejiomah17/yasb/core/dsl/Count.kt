package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Count<DRIVER_DATA_SOURCE>(
    private val expression: Expression<*, DRIVER_DATA_SOURCE>,
    private val databaseType: DatabaseType<Long, DRIVER_DATA_SOURCE>
) : AliasableExpressionForCondition<Long, DRIVER_DATA_SOURCE> {

    override fun databaseType(): DatabaseType<Long, DRIVER_DATA_SOURCE> {
        return databaseType
    }

    override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
        val queryPart = expression.build()
        return QueryPartImpl(
            sqlDefinition = "COUNT(${queryPart.sqlDefinition})",
            parameters = queryPart.parameters
        )
    }
}

context (DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <DRIVER_DATA_SOURCE> count(expression: Expression<*, DRIVER_DATA_SOURCE>): Count<DRIVER_DATA_SOURCE> {
    return Count(expression, longType())
}
