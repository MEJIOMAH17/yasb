package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.PostgresDatabaseDialect

object PostgresJdbcDatabaseDialect : PostgresDatabaseDialect {
    override fun booleanType(): JDBCDatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): JDBCDatabaseType<Long> {
        return LongDatabaseType
    }
}
