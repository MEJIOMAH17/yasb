package com.github.mejiomah17.yasb.core

interface DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun booleanType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun longType(): DatabaseType<Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
