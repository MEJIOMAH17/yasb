package com.github.mejiomah17.yasb.postgres.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.TextDatabaseType
import java.sql.PreparedStatement

class TextParameter(
    override val value: String?
) : PostgresParameter<String>() {
    override val databaseType: JDBCDatabaseType<String> = TextDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setString(index, value)
    }
}
