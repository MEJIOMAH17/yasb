package com.github.mejiomah17.yasb.postgres.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.BooleanDatabaseType
import java.sql.PreparedStatement

class BooleanParameter(
    override val value: Boolean?
) : PostgresParameter<Boolean>() {
    override val databaseType: JDBCDatabaseType<Boolean> = BooleanDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(index, value)
    }
}
