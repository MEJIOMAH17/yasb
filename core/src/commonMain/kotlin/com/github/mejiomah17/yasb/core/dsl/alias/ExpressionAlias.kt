package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class ExpressionAlias<T, DRIVER_DATA_SOURCE>(
    private val expression: AliasableExpression<T, DRIVER_DATA_SOURCE>,
    val name: String
) : Expression<T, DRIVER_DATA_SOURCE> {

    override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE> {
        return expression.databaseType()
    }

    override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
        val underlying = expression.build()
        return QueryPartImpl(
            sqlDefinition = "(${underlying.sqlDefinition}) AS $name",
            parameters = underlying.parameters
        )
    }
}

fun <T, DRIVER_DATA_SOURCE> Parameter<T, DRIVER_DATA_SOURCE>.`as`(name: String): ExpressionAlias<T, DRIVER_DATA_SOURCE> {
    return ExpressionAlias(
        expression = object : AliasableExpression<T, DRIVER_DATA_SOURCE> {
            override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE> {
                return this@`as`.databaseType
            }

            override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
                return QueryPartImpl(
                    sqlDefinition = this@`as`.parameterInSql,
                    parameters = listOf(this@`as`)
                )
            }
        },
        name = name
    )
}

fun <T, DRIVER_DATA_SOURCE> AliasableExpression<T, DRIVER_DATA_SOURCE>.`as`(name: String): ExpressionAlias<T, DRIVER_DATA_SOURCE> {
    return ExpressionAlias(
        expression = this,
        name = name
    )
}
