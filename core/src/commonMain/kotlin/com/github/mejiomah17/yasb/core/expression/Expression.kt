package com.github.mejiomah17.yasb.core.expression

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.query.QueryPart

interface Expression<T, S> {
    fun databaseType(): DatabaseType<T, S>
    fun build(): QueryPart<S>
}
