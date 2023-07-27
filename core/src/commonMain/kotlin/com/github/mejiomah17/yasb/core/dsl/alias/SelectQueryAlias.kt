package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter

class SelectQueryAlias<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val query: SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    val name: String
) :
    SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun sql(): String = "(${query.sql()}) AS $name"
    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = query.parameters()
    operator fun <V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> get(expression: ExpressionAlias<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                return expression.databaseType()
            }

            override fun sql(): String {
                return "$name.${expression.name}"
            }

            override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
                return emptyList()
            }
        }
    }

    operator fun <V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> get(column: Column<*, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                return column.databaseType()
            }

            override fun sql(): String {
                return "$name.${column.name}"
            }

            override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
                return emptyList()
            }
        }
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.`as`(name: String): SelectQueryAlias<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return SelectQueryAlias(query = this, name = name)
}
