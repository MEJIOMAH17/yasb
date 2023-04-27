package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.parameter.UuidParameter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

object UuidDatabaseType : JDBCDatabaseType<UUID> {
    override fun extractFromSource(resultSet: ResultSet, index: Int): UUID? {
        return resultSet.getString(index)?.let { UUID.fromString(it) }
    }

    override fun parameterFactory(): (UUID?) -> Parameter<UUID, ResultSet, PreparedStatement> = ::UuidParameter
}
