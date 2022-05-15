package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.parameter.PostgresParameter
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TimestampNullableDatabaseType
import java.sql.Timestamp
import java.time.Instant

class TimestampParameter(
    override val value: Timestamp
) : PostgresParameter<Timestamp>() {
    override val databaseType: DatabaseType<Timestamp> = TimestampDatabaseType
}

class TimestampNullableParameter(
    override val value: Timestamp?
) : PostgresParameter<Timestamp?>() {
    override val databaseType: DatabaseType<Timestamp?> = TimestampNullableDatabaseType
}

