package com.github.mejiomah17.yasb.postgres.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.LongDatabaseType
import java.sql.PreparedStatement

class LongParameter(
    override val value: Long?
) : PostgresParameter<Long>() {
    override val databaseType: JDBCDatabaseType<Long> = LongDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(index, value)
    }
}
