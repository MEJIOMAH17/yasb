package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Count<S>(
    private val expression: Expression<*, S>,
    private val databaseType: DatabaseType<Long, S>
) : AliasableExpressionForCondition<Long, S> {

    override fun databaseType(): DatabaseType<Long, S> {
        return databaseType
    }

    override fun build(): QueryPart<S> {
        val queryPart = expression.build()
        return QueryPartImpl(
            sqlDefinition = "COUNT(${queryPart.sqlDefinition})",
            parameters = queryPart.parameters
        )
    }
}

context (DatabaseDialect<S>)
fun <S> count(expression: Expression<*, S>): Count<S> {
    return Count(expression, longType())
}
