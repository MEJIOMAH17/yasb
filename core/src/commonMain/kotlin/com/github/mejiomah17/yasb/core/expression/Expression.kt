package com.github.mejiomah17.yasb.core.expression

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.query.QueryPart

interface Expression<T, DRIVER_DATA_SOURCE> {
    fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE>
    fun build(): QueryPart<DRIVER_DATA_SOURCE>
}
