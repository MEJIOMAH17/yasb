package com.github.mejiomah17.sqlite.android.parameter

import com.github.mejiomah17.yasb.core.parameter.Parameter

abstract class SqliteParameter<T> : Parameter<T> {
    override val parameterInSql: String = "?"
}
