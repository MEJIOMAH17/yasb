package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val expression: AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val name: String
) : Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return expression.databaseType()
    }

    override fun sql(): String {
        return "(${expression.sql()}) AS $name"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return expression.parameters()
    }
}

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.`as`(name: String): ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return ExpressionAlias(
        expression = object : AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                return this@`as`.databaseType
            }

            override fun sql(): String {
                return this@`as`.parameterInSql
            }

            override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
                return listOf(this@`as`)
            }
        },
        name = name
    )
}

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.`as`(name: String): ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return ExpressionAlias(
        expression = this,
        name = name
    )
}
