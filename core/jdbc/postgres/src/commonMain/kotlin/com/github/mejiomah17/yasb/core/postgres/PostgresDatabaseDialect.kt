package com.github.mejiomah17.yasb.core.postgres

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType

object PostgresDatabaseDialect : DatabaseDialect, SupportsInsertWithDefaultValue, SupportsLimit {
    override fun booleanType(): JDBCDatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): JDBCDatabaseType<Long> {
        return LongDatabaseType
    }
}
