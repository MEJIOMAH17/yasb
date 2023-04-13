package com.github.mejiomah17.yasb.sqlite.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.TextParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object TextDatabaseType : JDBCDatabaseType<String> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): String? {
        return resultSet.getString(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<String>, statement: PreparedStatement, index: Int) {
        statement.setString(index, parameter.value)
    }

    override fun parameterFactory(): (String?) -> Parameter<String> = ::TextParameter
}
