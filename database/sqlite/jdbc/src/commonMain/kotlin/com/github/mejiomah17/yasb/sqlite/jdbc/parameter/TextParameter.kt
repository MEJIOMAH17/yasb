package com.github.mejiomah17.yasb.sqlite.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.TextDatabaseType

class TextParameter(
    override val value: String?
) : SqliteParameter<String>() {
    override val databaseType: JDBCDatabaseType<String> = TextDatabaseType
}
