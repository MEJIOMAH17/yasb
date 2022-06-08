package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.parameter.PostgresParameter
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import java.sql.Timestamp

class TimestampParameter(
    override val value: Timestamp?
) : PostgresParameter<Timestamp>() {
    override val databaseType: DatabaseType<Timestamp> = TimestampDatabaseType
}

