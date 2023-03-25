package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.parameter.PostgresParameter
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import java.sql.Timestamp

class TimestampParameter(
    override val value: Timestamp?
) : PostgresParameter<Timestamp>() {
    override val databaseType: JDBCDatabaseType<Timestamp> = TimestampDatabaseType
}
