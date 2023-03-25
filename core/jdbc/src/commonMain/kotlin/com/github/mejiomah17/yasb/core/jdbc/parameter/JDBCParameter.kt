package com.github.mejiomah17.yasb.core.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter

interface JDBCParameter<T> : Parameter<T> {
    override val databaseType: JDBCDatabaseType<T>
}
