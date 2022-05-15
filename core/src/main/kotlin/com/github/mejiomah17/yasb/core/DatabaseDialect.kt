package com.github.mejiomah17.yasb.core

interface DatabaseDialect {
    fun booleanType(): DatabaseType<Boolean>
}