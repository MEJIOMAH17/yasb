package com.github.mejiomah17.sqlite.android.parameter

import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : SqliteParameter<Boolean>() {
    override val databaseType: AndroidDatabaseType<Boolean> = BooleanDatabaseType
}
