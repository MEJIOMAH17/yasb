package com.github.mejiomah17.yaksb.core.ddl

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.expression.AliasableExpression
import com.github.mejiomah17.yaksb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yaksb.core.parameter.Parameter

class Column<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val name: String,
    val table: TABLE,
    val databaseType: DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
) : AliasableExpression<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = databaseType

    override fun sql(): String = "${table.tableName}.$name"

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = emptyList()

    override fun toString(): String = "${table.tableName}.$name"
}
