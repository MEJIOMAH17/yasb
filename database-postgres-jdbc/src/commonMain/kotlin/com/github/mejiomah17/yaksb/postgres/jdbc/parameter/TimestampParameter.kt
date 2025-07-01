package com.github.mejiomah17.yaksb.core.parameter

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.postgres.jdbc.parameter.PostgresParameter
import com.github.mejiomah17.yaksb.postgres.jdbc.type.TimestampDatabaseType
import java.sql.PreparedStatement
import java.sql.Timestamp

class TimestampParameter(
    override val value: Timestamp?,
) : PostgresParameter<Timestamp>() {
    override val databaseType: JDBCDatabaseType<Timestamp> = TimestampDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int,
    ) {
        statement.setTimestamp(index, value)
    }
}
