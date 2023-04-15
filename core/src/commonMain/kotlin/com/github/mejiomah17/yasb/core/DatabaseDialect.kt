package com.github.mejiomah17.yasb.core

interface DatabaseDialect<S> {
    fun booleanType(): DatabaseType<Boolean, S>
    fun longType(): DatabaseType<Long, S>
}
