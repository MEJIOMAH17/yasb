package com.github.mejiomah17.yasb.core.sqlite.parameter

import com.github.mejiomah17.yasb.core.jdbc.parameter.JDBCParameter

abstract class SqliteParameter<T> : JDBCParameter<T> {
    override val parameterInSql: String = "?"
}
