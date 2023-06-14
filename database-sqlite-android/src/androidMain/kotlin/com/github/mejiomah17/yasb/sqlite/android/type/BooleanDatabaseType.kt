package com.github.mejiomah17.yasb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.parameter.BooleanParameter

object BooleanDatabaseType : AndroidDatabaseType<Boolean> {

    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean, Cursor, AndroidSqliteDriverStatement> =
        ::BooleanParameter

    override fun extractFromSource(source: Cursor, index: Int): Boolean? {
        return when (source.getLong(index)) {
            0L -> false
            1L -> true
            else -> error("Unexpected value ${source.getLong(index)} for boolean")
        }
    }
}
