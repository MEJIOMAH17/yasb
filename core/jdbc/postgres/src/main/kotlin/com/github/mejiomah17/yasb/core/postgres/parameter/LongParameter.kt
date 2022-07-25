package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType

class LongParameter(
    override val value: Long?
) : PostgresParameter<Long>() {
    override val databaseType: JDBCDatabaseType<Long> = LongDatabaseType
}
