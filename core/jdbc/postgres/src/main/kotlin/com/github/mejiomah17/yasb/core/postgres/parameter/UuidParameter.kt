package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.parameter.PostgresParameter
import com.github.mejiomah17.yasb.core.postgres.type.UuidDatabaseType
import java.util.*

class UuidParameter(
    override val value: UUID?
) : PostgresParameter<UUID>() {
    override val databaseType: JDBCDatabaseType<UUID> = UuidDatabaseType
}
