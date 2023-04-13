package com.github.mejiomah17.yasb.sqlite.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.BooleanParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object BooleanDatabaseType : JDBCDatabaseType<Boolean> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Boolean? {
        return resultSet.getNullable {
            resultSet.getBoolean(index)
        }
    }

    override fun applyParameterToStatement(parameter: Parameter<Boolean>, statement: PreparedStatement, index: Int) {
        statement.setObject(index, parameter.value)
    }

    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean> = ::BooleanParameter
}
