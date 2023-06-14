package com.github.mejiomah17.yasb.sqlite.android.parameter

import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : SqliteParameter<Boolean>() {
    override val databaseType: AndroidDatabaseType<Boolean> = BooleanDatabaseType
    override fun applyToStatement(statement: AndroidSqliteDriverStatement, index: Int) {
        when (value) {
            null -> statement.bindNull(index)
            true -> statement.bindLong(index, 1)
            false -> statement.bindLong(index, 0)
        }
    }
}
