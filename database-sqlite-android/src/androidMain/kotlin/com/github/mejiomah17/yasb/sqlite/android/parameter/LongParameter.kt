package com.github.mejiomah17.yaksb.sqlite.android.parameter

import com.github.mejiomah17.yaksb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.type.LongDatabaseType

class LongParameter(
    override val value: Long?,
) : SqliteParameter<Long>() {
    override val databaseType: AndroidDatabaseType<Long> = LongDatabaseType

    override fun applyToStatement(
        statement: AndroidSqliteDriverStatement,
        index: Int,
    ) {
        if (value == null) {
            statement.bindNull(index)
        } else {
            statement.bindLong(index, value)
        }
    }
}
