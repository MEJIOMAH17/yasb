package com.github.mejiomah17.yasb.sqlite.android.parameter

import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.TextDatabaseType

class TextParameter(
    override val value: String?
) : SqliteParameter<String>() {
    override val databaseType: AndroidDatabaseType<String> = TextDatabaseType
    override fun applyToStatement(statement: AndroidSqliteDriverStatement, index: Int) {
        if (value == null) {
            statement.bindNull(index)
        } else {
            statement.bindString(index, value)
        }
    }
}
