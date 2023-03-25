package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Column<T : Table<*>, V>(
    val name: String,
    val table: T,
    val databaseType: DatabaseType<V>
) : AliasableExpression<V>, ExpressionForCondition<V> {
    override fun build(): QueryPart {
        return QueryPartImpl(
            sqlDefinition = "${table.tableName}.$name",
            parameters = emptyList()
        )
    }

    override fun databaseType(): DatabaseType<V> {
        return databaseType
    }

    override fun toString(): String {
        return "${table.tableName}.$name"
    }
}
