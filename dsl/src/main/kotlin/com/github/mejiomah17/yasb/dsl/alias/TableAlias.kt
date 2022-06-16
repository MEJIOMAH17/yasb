package com.github.mejiomah17.yasb.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class TableAlias<T : Table<T>>(val table: T, val name: String) : SelectionSource {
    operator fun <V> get(column: Column<T, V>): AliasableExpression<V> {
        return object : AliasableExpression<V> {
            override fun databaseType(): DatabaseType<V> {
                return column.databaseType
            }

            override fun build(): QueryPart {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = emptyList()
                )
            }

        }
    }

    override val sqlDefinition: String = "${table.sqlDefinition} AS $name"
    override val parameters: List<Parameter<*>> = table.parameters
}

fun <T : Table<T>> T.`as`(name: String): TableAlias<T> {
    return TableAlias(table = this, name = name)
}