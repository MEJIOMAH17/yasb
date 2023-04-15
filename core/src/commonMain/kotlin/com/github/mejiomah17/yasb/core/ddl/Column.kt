package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

class Column<T : Table<T, S>, V, S>(
    val name: String,
    val table: T,
    val databaseType: DatabaseType<V, S>
) : AliasableExpression<V, S>, ExpressionForCondition<V, S> {
    override fun build(): QueryPart<S> {
        return QueryPartImpl(
            sqlDefinition = "${table.tableName}.$name",
            parameters = emptyList()
        )
    }

    override fun databaseType(): DatabaseType<V, S> {
        return databaseType
    }

    override fun toString(): String {
        return "${table.tableName}.$name"
    }
}
