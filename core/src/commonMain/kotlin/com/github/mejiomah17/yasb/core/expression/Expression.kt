package com.github.mejiomah17.yasb.core.expression

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.query.QueryPart

interface Expression<T> {
    fun databaseType(): DatabaseType<T>
    fun build(): QueryPart
}
