package com.github.mejiomah17.yasb.core.sqlite.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.sqlite.type.LongDatabaseType

class LongParameter(
    override val value: Long?
) : SqliteParameter<Long>() {
    override val databaseType: JDBCDatabaseType<Long> = LongDatabaseType
}
