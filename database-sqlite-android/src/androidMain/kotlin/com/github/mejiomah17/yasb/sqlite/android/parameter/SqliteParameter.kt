package com.github.mejiomah17.yasb.sqlite.android.parameter

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter

abstract class SqliteParameter<T> : Parameter<T, Cursor, AndroidSqliteDriverStatement> {
    override val parameterInSql: String = "?"
}
