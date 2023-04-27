package com.github.mejiomah17.yasb.core.jdbc

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JDBCDatabaseType<T> : DatabaseType<T, ResultSet, PreparedStatement> {
    /**
     * Always return T? because in left/right/full join every column becomes nullable
     */
    fun applyParameterToStatement(
        parameter: Parameter<T, ResultSet, PreparedStatement>,
        statement: PreparedStatement,
        index: Int
    )
}
