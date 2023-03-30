package com.github.mejiomah17.yasb.core.sqlite.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.sqlite.type.TextDatabaseType

class TextParameter(
    override val value: String?
) : SqliteParameter<String>() {
    override val databaseType: JDBCDatabaseType<String> = TextDatabaseType
}
