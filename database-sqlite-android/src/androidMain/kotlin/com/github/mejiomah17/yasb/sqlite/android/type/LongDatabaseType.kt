package com.github.mejiomah17.yasb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.parameter.LongParameter

object LongDatabaseType : AndroidDatabaseType<Long> {
    override fun parameterFactory(): (Long?) -> Parameter<Long, Cursor, AndroidSqliteDriverStatement> = ::LongParameter
    override fun extractFromSource(source: Cursor, index: Int): Long? {
        return source.getLong(index)
    }
}
