package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.DoublePrecisionDatabaseType

class DoubleParameter(
    override val value: Double?
) : PostgresParameter<Double>() {
    override val databaseType: JDBCDatabaseType<Double> = DoublePrecisionDatabaseType
}
