package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.JsonbDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TextDatabaseType

class JsonbParameter(
    override val value: String?
) : PostgresParameter<String>() {
    override val databaseType: DatabaseType<String> = JsonbDatabaseType
}