package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : PostgresParameter<Boolean>() {
    override val databaseType: DatabaseType<Boolean> = BooleanDatabaseType
}