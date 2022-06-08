package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TextDatabaseType

class TextParameter(
    override val value: String?
) : PostgresParameter<String>() {
    override val databaseType: DatabaseType<String> = TextDatabaseType
}