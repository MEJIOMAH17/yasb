package com.github.mejiomah17.yasb.core.postgres

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.PostgresDatabaseDialect

object PostgresJdbcDatabaseDialect : PostgresDatabaseDialect {
    override fun booleanType(): JDBCDatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): JDBCDatabaseType<Long> {
        return LongDatabaseType
    }
}
