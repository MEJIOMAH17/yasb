package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class ExpressionAlias<T>(
    private val expression: Expression<T>,
    private val name: String
) : Expression<T> {

    override fun databaseType(): DatabaseType<T> {
        return expression.databaseType()
    }

    override fun build(): QueryPart {
        val underlying = expression.build()
        return QueryPartImpl(
            value = "(${underlying.value}) as $name",
            parameters = underlying.parameters
        )
    }
}

fun <T> Parameter<T>.`as`(name: String): ExpressionAlias<T> {
    return ExpressionAlias(
        expression = object : Expression<T> {
            override fun databaseType(): DatabaseType<T> {
                return this@`as`.databaseType
            }

            override fun build(): QueryPart {
                return QueryPartImpl(
                    value = this@`as`.parameterInJdbcQuery,
                    parameters = listOf(this@`as`)
                )
            }
        },
        name = name
    )
}