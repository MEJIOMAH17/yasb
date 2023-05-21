package com.github.mejiomah17.yasb.sqlite.android.parameter

import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.LongDatabaseType

class LongParameter(
    override val value: Long?
) : SqliteParameter<Long>() {
    override val databaseType: AndroidDatabaseType<Long> = LongDatabaseType
}
