package com.github.mejiomah17.yaksb.sqlite.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.parameter.JDBCParameter

abstract class SqliteParameter<T> : JDBCParameter<T> {
    override val parameterInSql: String = "?"
}
