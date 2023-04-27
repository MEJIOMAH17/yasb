package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class ExpressionAlias<T, S>(
    private val expression: AliasableExpression<T, S>,
    val name: String
) : Expression<T, S> {

    override fun databaseType(): DatabaseType<T, S> {
        return expression.databaseType()
    }

    override fun build(): QueryPart<S> {
        val underlying = expression.build()
        return QueryPartImpl(
            sqlDefinition = "(${underlying.sqlDefinition}) AS $name",
            parameters = underlying.parameters
        )
    }
}

fun <T, S> Parameter<T, S>.`as`(name: String): ExpressionAlias<T, S> {
    return ExpressionAlias(
        expression = object : AliasableExpression<T, S> {
            override fun databaseType(): DatabaseType<T, S> {
                return this@`as`.databaseType
            }

            override fun build(): QueryPart<S> {
                return QueryPartImpl(
                    sqlDefinition = this@`as`.parameterInSql,
                    parameters = listOf(this@`as`)
                )
            }
        },
        name = name
    )
}

fun <T, S> AliasableExpression<T, S>.`as`(name: String): ExpressionAlias<T, S> {
    return ExpressionAlias(
        expression = this,
        name = name
    )
}
