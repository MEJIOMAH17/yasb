package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.parameter.Parameter

/**
 * T - type
 * DRIVER_DATA_SOURCE - source like (ResultSet, Cursor, etc)
 */
interface DatabaseType<T, DRIVER_DATA_SOURCE> {
    fun parameterFactory(): (T?) -> Parameter<T, DRIVER_DATA_SOURCE>
    fun extractFromSource(source: DRIVER_DATA_SOURCE, index: Int): T?
}
