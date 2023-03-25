package com.github.mejiomah17.yasb.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class ExpressionAlias<T>(
    private val expression: AliasableExpression<T>,
    val name: String
) : Expression<T> {

    override fun databaseType(): DatabaseType<T> {
        return expression.databaseType()
    }

    override fun build(): QueryPart {
        val underlying = expression.build()
        return QueryPartImpl(
            sqlDefinition = "(${underlying.sqlDefinition}) AS $name",
            parameters = underlying.parameters
        )
    }
}

fun <T> Parameter<T>.`as`(name: String): ExpressionAlias<T> {
    return ExpressionAlias(
        expression = object : AliasableExpression<T> {
            override fun databaseType(): DatabaseType<T> {
                return this@`as`.databaseType
            }

            override fun build(): QueryPart {
                return QueryPartImpl(
                    sqlDefinition = this@`as`.parameterInSql,
                    parameters = listOf(this@`as`)
                )
            }
        },
        name = name
    )
}

fun <T> AliasableExpression<T>.`as`(name: String): ExpressionAlias<T> {
    return ExpressionAlias(
        expression = this,
        name = name
    )
}
