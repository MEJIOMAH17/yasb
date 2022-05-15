package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryWithParameters

class ExpressionAlias<T>(
    private val expression: Expression<T>,
    private val name: String
) : Expression<T> {

    override fun databaseType(): DatabaseType<T> {
        return expression.databaseType()
    }

    override fun build(): QueryWithParameters {
        val underlying = expression.build()
        return QueryWithParameters(
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

            override fun build(): QueryWithParameters {
                return QueryWithParameters(
                    value = this@`as`.parameterInJdbcQuery,
                    parameters = listOf(this@`as`)
                )
            }
        },
        name = name
    )
}