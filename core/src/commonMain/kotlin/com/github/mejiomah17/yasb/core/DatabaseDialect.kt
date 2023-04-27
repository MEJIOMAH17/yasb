package com.github.mejiomah17.yasb.core

interface DatabaseDialect<DRIVER_DATA_SOURCE> {
    fun booleanType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE>
    fun longType(): DatabaseType<Long, DRIVER_DATA_SOURCE>
}
