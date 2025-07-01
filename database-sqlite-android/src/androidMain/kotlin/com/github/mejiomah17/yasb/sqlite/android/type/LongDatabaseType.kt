package com.github.mejiomah17.yaksb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.parameter.LongParameter

object LongDatabaseType : AndroidDatabaseType<Long> {
    override fun parameterFactory(): (Long?) -> Parameter<Long, Cursor, AndroidSqliteDriverStatement> = ::LongParameter

    override fun extractFromSource(
        source: Cursor,
        index: Int,
    ): Long? {
        return source.getLong(index)
    }
}
