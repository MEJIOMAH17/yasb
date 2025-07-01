package com.github.mejiomah17.yaksb.core.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JDBCParameter<T> : Parameter<T, ResultSet, PreparedStatement> {
    override val databaseType: JDBCDatabaseType<T>
}
