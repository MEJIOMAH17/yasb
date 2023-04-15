package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.parameter.Parameter

/**
 * T - type
 * S - source like (ResultSet, Cursor, etc)
 */
interface DatabaseType<T, S> {
    fun parameterFactory(): (T?) -> Parameter<T, S>
    fun extractFromSource(source: S, index: Int): T?
}
