package com.github.mejiomah17.yasb.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl
import com.github.mejiomah17.yasb.dsl.SelectQuery

class SelectQueryAlias<S>(val source: SelectQuery<S>, val name: String) : SelectionSource<S> {
    private val query = source.buildSelectQuery()
    override val sqlDefinition: String = "(${query.sqlDefinition}) AS $name"
    override val parameters: List<Parameter<*, S>> = query.parameters
    operator fun <V, S> get(expression: ExpressionAlias<V, S>): AliasableExpressionForCondition<V, S> {
        return object : AliasableExpressionForCondition<V, S> {
            override fun databaseType(): DatabaseType<V, S> {
                return expression.databaseType()
            }

            override fun build(): QueryPart<S> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${expression.name}",
                    parameters = emptyList()
                )
            }
        }
    }

    operator fun <V, S> get(column: Column<*, V, S>): AliasableExpressionForCondition<V, S> {
        return object : AliasableExpressionForCondition<V, S> {
            override fun databaseType(): DatabaseType<V, S> {
                return column.databaseType()
            }

            override fun build(): QueryPart<S> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = emptyList()
                )
            }
        }
    }
}

fun <S> SelectQuery<S>.`as`(name: String): SelectQueryAlias<S> {
    return SelectQueryAlias(source = this, name = name)
}
