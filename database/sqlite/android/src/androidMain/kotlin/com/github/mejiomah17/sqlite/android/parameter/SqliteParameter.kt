package com.github.mejiomah17.sqlite.android.parameter

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter

abstract class SqliteParameter<T> : Parameter<T, Cursor> {
    override val parameterInSql: String = "?"
}
