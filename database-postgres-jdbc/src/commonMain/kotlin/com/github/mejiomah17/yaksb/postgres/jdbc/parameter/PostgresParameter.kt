package com.github.mejiomah17.yaksb.postgres.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.parameter.JDBCParameter

abstract class PostgresParameter<T> : JDBCParameter<T> {
    override val parameterInSql: String = "?"
}
