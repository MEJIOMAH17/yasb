package com.github.mejiomah17.yaksb.sqlite.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.LongDatabaseType
import java.sql.PreparedStatement

class LongParameter(
    override val value: Long?
) : SqliteParameter<Long>() {
    override val databaseType: JDBCDatabaseType<Long> = LongDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(index, value)
    }
}
