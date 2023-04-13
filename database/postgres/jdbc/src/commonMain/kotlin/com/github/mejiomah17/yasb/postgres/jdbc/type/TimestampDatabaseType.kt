package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.parameter.TimestampParameter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

object TimestampDatabaseType : JDBCDatabaseType<Timestamp> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Timestamp? {
        return resultSet.getTimestamp(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<Timestamp>, statement: PreparedStatement, index: Int) {
        statement.setTimestamp(index, parameter.value)
    }

    override fun parameterFactory(): (Timestamp?) -> Parameter<Timestamp> = ::TimestampParameter
}
