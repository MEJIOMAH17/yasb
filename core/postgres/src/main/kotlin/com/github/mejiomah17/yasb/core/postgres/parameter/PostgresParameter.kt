package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.parameter.Parameter

abstract class PostgresParameter<T> : Parameter<T> {
    override val parameterInJdbcQuery: String = "?"
}