package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType

interface Parameter<T, DRIVER_DATA_SOURCE> {
    val value: T?
    val databaseType: DatabaseType<T, DRIVER_DATA_SOURCE>
    val parameterInSql: String
}
