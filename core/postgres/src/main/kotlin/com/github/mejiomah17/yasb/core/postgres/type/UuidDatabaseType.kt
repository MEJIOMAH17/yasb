package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.parameter.UuidParameter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*
import org.postgresql.util.PGobject

object UuidDatabaseType : DatabaseType<UUID> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): UUID {
        return UUID.fromString(resultSet.getString(index))
    }

    override fun applyParameterToStatement(parameter: Parameter<UUID>, statement: PreparedStatement, index: Int) {
        statement.setObject(
            index,
            PGobject().apply {
                type = "uuid"
                value = parameter.value.toString()
            }
        )
    }

    override fun parameterFactory(): (UUID?) -> Parameter<UUID> = ::UuidParameter
}
