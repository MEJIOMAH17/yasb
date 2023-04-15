package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType

interface Parameter<T, S> {
    val value: T?
    val databaseType: DatabaseType<T, S>
    val parameterInSql: String
}
