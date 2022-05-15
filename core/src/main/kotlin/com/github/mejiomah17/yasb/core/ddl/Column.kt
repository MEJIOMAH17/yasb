package com.github.mejiomah17.yasb.core.ddl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.query.QueryWithParameters

class Column<T : Table<*>, V>(
    val name: String,
    val table: T,
    val databaseType: DatabaseType<V>
) : Expression<V> {
    override fun build(): QueryWithParameters {
        return QueryWithParameters(
            value = "${table.tableName}.$name",
            parameters = emptyList()
        )
    }

    override fun databaseType(): DatabaseType<V> {
        return databaseType
    }
}