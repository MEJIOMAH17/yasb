package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class SelectQueryAlias<DRIVER_DATA_SOURCE>(val source: SelectQuery<DRIVER_DATA_SOURCE>, val name: String) :
    SelectionSource<DRIVER_DATA_SOURCE> {
    private val query = source.buildSelectQuery()
    override val sqlDefinition: String = "(${query.sqlDefinition}) AS $name"
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE>> = query.parameters
    operator fun <V, DRIVER_DATA_SOURCE> get(expression: ExpressionAlias<V, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE> {
        return object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE> {
                return expression.databaseType()
            }

            override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${expression.name}",
                    parameters = emptyList()
                )
            }
        }
    }

    operator fun <V, DRIVER_DATA_SOURCE> get(column: Column<*, V, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE> {
        return object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE> {
                return column.databaseType()
            }

            override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = emptyList()
                )
            }
        }
    }
}

fun <DRIVER_DATA_SOURCE> SelectQuery<DRIVER_DATA_SOURCE>.`as`(name: String): SelectQueryAlias<DRIVER_DATA_SOURCE> {
    return SelectQueryAlias(source = this, name = name)
}
