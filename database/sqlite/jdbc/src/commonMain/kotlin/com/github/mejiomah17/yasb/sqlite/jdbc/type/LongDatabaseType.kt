package com.github.mejiomah17.yasb.sqlite.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.LongParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object LongDatabaseType : JDBCDatabaseType<Long> {
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
