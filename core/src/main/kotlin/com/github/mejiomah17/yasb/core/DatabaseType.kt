package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.parameter.Parameter
import java.sql.PreparedStatement
import java.sql.ResultSet

interface DatabaseType<T> {
    fun parameterFactory(): (T?) -> Parameter<T>

    /**
     * Always return T? because in left/right/full join every column becomes nullable
     */
    fun extractFromResultSet(resultSet: ResultSet, index: Int): T?
    fun applyParameterToStatement(parameter: Parameter<T>, statement: PreparedStatement, index: Int)
}
