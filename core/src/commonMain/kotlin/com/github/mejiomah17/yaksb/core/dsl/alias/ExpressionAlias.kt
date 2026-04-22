package com.github.mejiomah17.yaksb.core.dsl.alias

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.expression.AliasableExpression
import com.github.mejiomah17.yaksb.core.expression.Expression
import com.github.mejiomah17.yaksb.core.parameter.Parameter

class ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    private val expression: AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val name: String,
) : Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = expression.databaseType()

    override fun sql(): String = "(${expression.sql()}) AS $name"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = expression.parameters()
}

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.`as`(
    name: String,
): ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    ExpressionAlias(
        expression =
            object : AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                override fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = this@`as`.databaseType

                override fun sql(): String = this@`as`.parameterInSql

                override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = listOf(this@`as`)
            },
        name = name,
    )

fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.`as`(
    name: String,
): ExpressionAlias<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    ExpressionAlias(
        expression = this,
        name = name,
    )
