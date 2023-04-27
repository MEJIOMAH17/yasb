package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.parameter.UuidParameter
import org.postgresql.util.PGobject
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

object UuidDatabaseType : JDBCDatabaseType<UUID> {
    override fun extractFromSource(resultSet: ResultSet, index: Int): UUID? {
        return resultSet.getString(index)?.let { UUID.fromString(it) }
    }

    override fun applyParameterToStatement(
        parameter: Parameter<UUID, ResultSet, PreparedStatement>,
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(
            index,
            PGobject().apply {
                type = "uuid"
                value = parameter.value?.toString()
            }
        )
    }

    override fun parameterFactory(): (UUID?) -> Parameter<UUID, ResultSet, PreparedStatement> = ::UuidParameter
}
