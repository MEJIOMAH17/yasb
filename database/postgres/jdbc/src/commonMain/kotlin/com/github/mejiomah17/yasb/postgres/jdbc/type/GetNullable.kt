package com.github.mejiomah17.yasb.postgres.jdbc.type

import java.sql.ResultSet

internal fun <T> ResultSet.getNullable(getter: (ResultSet.() -> T)): T? {
    val value = this.getter()
    return if (wasNull()) {
        null
    } else {
        value
    }
}
