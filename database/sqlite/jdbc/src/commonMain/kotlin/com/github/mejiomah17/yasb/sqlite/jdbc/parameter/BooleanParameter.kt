package com.github.mejiomah17.yasb.sqlite.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType

class BooleanParameter(
    override val value: Boolean?
) : SqliteParameter<Boolean>() {
    override val databaseType: JDBCDatabaseType<Boolean> = BooleanDatabaseType
}
