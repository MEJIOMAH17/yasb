package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.PostgresParameter
import com.github.mejiomah17.yasb.postgres.jdbc.type.UuidDatabaseType
import java.util.*

class UuidParameter(
    override val value: UUID?
) : PostgresParameter<UUID>() {
    override val databaseType: JDBCDatabaseType<UUID> = UuidDatabaseType
}
