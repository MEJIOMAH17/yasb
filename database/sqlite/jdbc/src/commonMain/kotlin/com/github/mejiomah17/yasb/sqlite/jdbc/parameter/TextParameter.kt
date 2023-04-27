package com.github.mejiomah17.yasb.sqlite.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.TextDatabaseType
import java.sql.PreparedStatement

class TextParameter(
    override val value: String?
) : SqliteParameter<String>() {
    override val databaseType: JDBCDatabaseType<String> = TextDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setString(index, value)
    }
}
