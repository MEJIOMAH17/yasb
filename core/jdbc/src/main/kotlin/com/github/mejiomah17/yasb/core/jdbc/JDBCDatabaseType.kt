package com.github.mejiomah17.yasb.core.jdbc

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JDBCDatabaseType<T> : DatabaseType<T> {
    /**
     * Always return T? because in left/right/full join every column becomes nullable
     */
    fun extractFromResultSet(resultSet: ResultSet, index: Int): T?
    fun applyParameterToStatement(parameter: Parameter<T>, statement: PreparedStatement, index: Int)
}
