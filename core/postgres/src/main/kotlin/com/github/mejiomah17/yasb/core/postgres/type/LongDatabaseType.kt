package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.LongParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object LongDatabaseType : DatabaseType<Long> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Long? {
        return resultSet.getNullable {
            resultSet.getLong(index)
        }
    }

    override fun applyParameterToStatement(parameter: Parameter<Long>, statement: PreparedStatement, index: Int) {
        statement.setObject(index, parameter.value)
    }

    override fun parameterFactory(): (Long?) -> Parameter<Long> = ::LongParameter
}