package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.DoublePrecisionDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType

class LongParameter(
    override val value: Long?
) : PostgresParameter<Long>() {
    override val databaseType: DatabaseType<Long> = LongDatabaseType
}