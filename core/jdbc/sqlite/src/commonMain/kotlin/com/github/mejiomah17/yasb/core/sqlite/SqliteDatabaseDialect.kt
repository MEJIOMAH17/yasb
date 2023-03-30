package com.github.mejiomah17.yasb.core.sqlite

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.sqlite.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.sqlite.type.LongDatabaseType

object SqliteDatabaseDialect : DatabaseDialect, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long> {
        return LongDatabaseType
    }
}
