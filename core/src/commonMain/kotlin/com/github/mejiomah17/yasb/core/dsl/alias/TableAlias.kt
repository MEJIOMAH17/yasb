package com.github.mejiomah17.yasb.core.dsl.alias

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SelectionSource
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class TableAlias<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val table: TABLE,
    val name: String
) :
    SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    operator fun <V> get(column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                return column.databaseType
            }

            override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
                return QueryPartImpl(
                    sqlDefinition = "$name.${column.name}",
                    parameters = column.build().parameters
                )
            }
        }
    }

    override val sqlDefinition: String = "${table.sqlDefinition} AS $name"
    override val parameters: List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = table.parameters
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> TABLE.`as`(name: String): TableAlias<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return TableAlias(table = this, name = name)
}
