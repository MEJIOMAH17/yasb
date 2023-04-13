package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.JsonbParameter
import org.postgresql.util.PGobject
import java.sql.PreparedStatement
import java.sql.ResultSet

object JsonbDatabaseType : JDBCDatabaseType<String> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): String? {
        return resultSet.getString(index)
    }

    override fun applyParameterToStatement(parameter: Parameter<String>, statement: PreparedStatement, index: Int) {
        statement.setObject(
            index,
            PGobject().apply {
                type = "jsonb"
                value = parameter.value?.toString()
            }
        )
    }

    override fun parameterFactory(): (String?) -> Parameter<String> = ::JsonbParameter
}
