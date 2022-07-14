package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.DoublePrecisionDatabaseType

class DoubleParameter(
    override val value: Double?
) : PostgresParameter<Double>() {
    override val databaseType: DatabaseType<Double> = DoublePrecisionDatabaseType
}