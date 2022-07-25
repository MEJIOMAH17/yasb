package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.jdbc.parameter.JDBCParameter

abstract class PostgresParameter<T> : JDBCParameter<T> {
    override val parameterInSql: String = "?"
}
