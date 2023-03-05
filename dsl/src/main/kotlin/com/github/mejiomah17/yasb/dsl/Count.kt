package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Count(
    private val expression: Expression<*>,
    private val databaseType: DatabaseType<Long>
) : AliasableExpressionForCondition<Long> {

    override fun databaseType(): DatabaseType<Long> {
        return databaseType
    }

    override fun build(): QueryPart {
        val queryPart = expression.build()
        return QueryPartImpl(
            sqlDefinition = "COUNT(${queryPart.sqlDefinition})",
            parameters = queryPart.parameters
        )
    }
}

context (DatabaseDialect)
fun count(expression: Expression<*>): Count {
    return Count(expression, longType())
}
