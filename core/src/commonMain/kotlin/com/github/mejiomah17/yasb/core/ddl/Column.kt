package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Column<T : Table<T, DRIVER_DATA_SOURCE>, V, DRIVER_DATA_SOURCE>(
    val name: String,
    val table: T,
    val databaseType: DatabaseType<V, DRIVER_DATA_SOURCE>
) : AliasableExpression<V, DRIVER_DATA_SOURCE>, ExpressionForCondition<V, DRIVER_DATA_SOURCE> {
    override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
        return QueryPartImpl(
            sqlDefinition = "${table.tableName}.$name",
            parameters = emptyList()
        )
    }

    override fun databaseType(): DatabaseType<V, DRIVER_DATA_SOURCE> {
        return databaseType
    }

    override fun toString(): String {
        return "${table.tableName}.$name"
    }
}
