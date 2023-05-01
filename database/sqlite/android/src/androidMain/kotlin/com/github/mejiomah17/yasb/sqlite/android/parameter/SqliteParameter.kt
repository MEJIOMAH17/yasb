package com.github.mejiomah17.yasb.sqlite.android.parameter

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter

abstract class SqliteParameter<T> : Parameter<T, Cursor, (String) -> Unit> {
    override val parameterInSql: String = "?"
    override fun applyToStatement(statement: (String) -> Unit, index: Int) {
        TODO("Not yet implemented")
    }
}
