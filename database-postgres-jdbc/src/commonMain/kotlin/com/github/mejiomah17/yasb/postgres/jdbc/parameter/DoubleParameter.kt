package com.github.mejiomah17.yasb.postgres.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.DoublePrecisionDatabaseType
import java.sql.PreparedStatement

class DoubleParameter(
    override val value: Double?
) : PostgresParameter<Double>() {
    override val databaseType: JDBCDatabaseType<Double> = DoublePrecisionDatabaseType
    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(index, value)
    }
}
