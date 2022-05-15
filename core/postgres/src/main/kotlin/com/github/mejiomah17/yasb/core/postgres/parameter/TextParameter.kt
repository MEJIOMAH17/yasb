package com.github.mejiomah17.yasb.core.postgres.parameter

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.type.TextDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TextNullableDatabaseType

class TextParameter(
    override val value: String
) : PostgresParameter<String>() {
    override val databaseType: DatabaseType<String> = TextDatabaseType
    override val parameterInJdbcQuery: String = "?"
}

class TextNullableParameter(
    override val value: String?
) : PostgresParameter<String?>() {
    override val databaseType: DatabaseType<String?> = TextNullableDatabaseType
    override val parameterInJdbcQuery: String = "?"
}