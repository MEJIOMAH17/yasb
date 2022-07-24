package com.github.mejiomah17.yasb.core.postgres

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType

object PostgresDatabaseDialect : DatabaseDialect {
    override fun booleanType(): DatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long> {
        return LongDatabaseType
    }
}