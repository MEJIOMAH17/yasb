package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType

interface Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val value: T?
    val databaseType: DatabaseType<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    val parameterInSql: String
}
