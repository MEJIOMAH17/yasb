package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.parameter.TimestampNullableParameter
import com.github.mejiomah17.yasb.core.parameter.TimestampParameter
import com.github.mejiomah17.yasb.core.parameter.UuidNullableParameter
import com.github.mejiomah17.yasb.core.parameter.UuidParameter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

object TimestampDatabaseType : DatabaseType<Timestamp> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Timestamp {
        return resultSet.getTimestamp(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<Timestamp>, statement: PreparedStatement, index: Int) {
        statement.setTimestamp(index, parameter.value)
    }

    override fun parameterFactory(): (Timestamp) -> Parameter<Timestamp> = ::TimestampParameter
}

object TimestampNullableDatabaseType : DatabaseType<Timestamp?> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Timestamp? {
        return resultSet.getTimestamp(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<Timestamp?>, statement: PreparedStatement, index: Int) {
        statement.setTimestamp(index, parameter.value)
    }

    override fun parameterFactory(): (Timestamp?) -> Parameter<Timestamp?> = ::TimestampNullableParameter
}
