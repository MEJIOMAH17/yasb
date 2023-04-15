package com.github.mejiomah17.yasb.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class TableAlias<T : Table<T, S>, S>(val table: T, val name: String) : SelectionSource<S> {

    operator fun <V> get(column: Column<T, V, S>): AliasableExpressionForCondition<V, S> {
        return object : AliasableExpressionForCondition<V, S> {
            override fun databaseType(): DatabaseType<V, S> {
                return column.databaseType
            }

            override fun build(): QueryPart<S> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = column.build().parameters
                )
            }
        }
    }

    override val sqlDefinition: String = "${table.sqlDefinition} AS $name"
    override val parameters: List<Parameter<*, S>> = table.parameters
}

fun <T : Table<T, S>, S> T.`as`(name: String): TableAlias<T, S> {
    return TableAlias(table = this, name = name)
}
