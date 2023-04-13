package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.LongDatabaseType

object SqliteJdbcDatabaseDialect : DatabaseDialect, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long> {
        return LongDatabaseType
    }
}
