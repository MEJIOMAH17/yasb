package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : PostgresParameter<Boolean>() {
    override val databaseType: JDBCDatabaseType<Boolean> = BooleanDatabaseType
}
