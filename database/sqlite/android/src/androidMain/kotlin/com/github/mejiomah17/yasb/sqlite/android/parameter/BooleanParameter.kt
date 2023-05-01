package com.github.mejiomah17.yasb.sqlite.android.parameter

import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : SqliteParameter<Boolean>() {
    override val databaseType: AndroidDatabaseType<Boolean> = BooleanDatabaseType
    override fun applyToStatement(statement: (String) -> Unit, index: Int) {
        statement.invoke(value.toString())
    }
}