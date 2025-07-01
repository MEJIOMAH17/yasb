package com.github.mejiomah17.yaksb.core.expression

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.query.QueryPart

interface Expression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun databaseType(): DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
