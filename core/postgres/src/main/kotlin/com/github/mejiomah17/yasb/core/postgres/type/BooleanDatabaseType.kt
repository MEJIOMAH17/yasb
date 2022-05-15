package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.BooleanParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object BooleanDatabaseType : DatabaseType<Boolean> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Boolean {
        return resultSet.getBoolean(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<Boolean>, statement: PreparedStatement, index: Int) {
        statement.setBoolean(index, parameter.value)
    }

    override fun parameterFactory(): (Boolean) -> Parameter<Boolean> = ::BooleanParameter
}