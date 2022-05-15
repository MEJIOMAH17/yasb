package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.TextParameter
import com.github.mejiomah17.yasb.core.postgres.parameter.TextNullableParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object TextDatabaseType : DatabaseType<String> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): String {
        return resultSet.getString(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<String>, statement: PreparedStatement, index: Int) {
        statement.setString(index, parameter.value)
    }

    override fun parameterFactory(): (String) -> Parameter<String> = ::TextParameter
}

object TextNullableDatabaseType : DatabaseType<String?> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): String? {
        return resultSet.getString(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<String?>, statement: PreparedStatement, index: Int) {
        statement.setString(index, parameter.value)
    }

    override fun parameterFactory(): (String?) -> Parameter<String?> = ::TextNullableParameter
}