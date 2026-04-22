package com.github.mejiomah17.yaksb.core.dsl.alias

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.SelectionSource
import com.github.mejiomah17.yaksb.core.ddl.Column
import com.github.mejiomah17.yaksb.core.ddl.Table
import com.github.mejiomah17.yaksb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yaksb.core.parameter.Parameter

class TableAlias<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val table: TABLE,
    val name: String,
) : SelectionSource<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    operator fun <V> get(
        column: Column<TABLE, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ): AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
        object : AliasableExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = column.databaseType

            override fun sql(): String = "$name.${column.name}"

            override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = column.parameters()
        }

    override fun sql(): String = "${table.sql()} AS $name"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = table.parameters()
}

fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> TABLE.`as`(
    name: String,
): TableAlias<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = TableAlias(table = this, name = name)
