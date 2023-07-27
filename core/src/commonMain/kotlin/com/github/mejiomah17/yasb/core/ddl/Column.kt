package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter

class Column<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
    val name: String,
    val table: TABLE,
    val databaseType: DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
) : AliasableExpression<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ExpressionForCondition<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return databaseType
    }

    override fun sql(): String {
        return "${table.tableName}.$name"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return emptyList()
    }

    override fun toString(): String {
        return "${table.tableName}.$name"
    }
}
