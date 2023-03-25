package com.github.mejiomah17.yasb.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl
import com.github.mejiomah17.yasb.dsl.SelectQuery

class SelectQueryAlias(val source: SelectQuery, val name: String) : SelectionSource {
    private val query = source.buildSelectQuery()
    override val sqlDefinition: String = "(${query.sqlDefinition}) AS $name"
    override val parameters: List<Parameter<*>> = query.parameters
    operator fun <V> get(expression: ExpressionAlias<V>): AliasableExpressionForCondition<V> {
        return object : AliasableExpressionForCondition<V> {
            override fun databaseType(): DatabaseType<V> {
                return expression.databaseType()
            }

            override fun build(): QueryPart {
                return QueryPartImpl(
                    sqlDefinition = "$name.${expression.name}",
                    parameters = emptyList()
                )
            }
        }
    }

    operator fun <V> get(column: Column<*, V>): AliasableExpressionForCondition<V> {
        return object : AliasableExpressionForCondition<V> {
            override fun databaseType(): DatabaseType<V> {
                return column.databaseType()
            }

            override fun build(): QueryPart {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = emptyList()
                )
            }
        }
    }
}

fun SelectQuery.`as`(name: String): SelectQueryAlias {
    return SelectQueryAlias(source = this, name = name)
}
