package com.github.mejiomah17.sqlite.android.parameter

import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.type.LongDatabaseType

class LongParameter(
    override val value: Long?
) : SqliteParameter<Long>() {
    override val databaseType: AndroidDatabaseType<Long> = LongDatabaseType
}
