package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType

/**
 * We can't use generics for abstract DatabaseType in Conditions. (Like T:DatabaseType).
 * So, this check can't be executed in compile time. This is a runtime cast for JDBCDatabaseType.
 */
internal fun <T> DatabaseType<T, *>.jdbc(): JDBCDatabaseType<T> {
    return this as? JDBCDatabaseType<T>
        ?: error("type ${this::class.qualifiedName} used in JDBCTransaction. It has to be a JDBCDatabaseType")
}
