package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.parameter.PostgresParameter
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.UuidDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.UuidNullableDatabaseType
import java.util.UUID

class UuidParameter(
    override val value: UUID
) : PostgresParameter<UUID>() {
    override val databaseType: DatabaseType<UUID> = UuidDatabaseType
}

class UuidNullableParameter(
    override val value: UUID?
) : PostgresParameter<UUID?>() {
    override val databaseType: DatabaseType<UUID?> = UuidNullableDatabaseType
}

