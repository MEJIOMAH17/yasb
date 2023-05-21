package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.BooleanParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object BooleanDatabaseType : JDBCDatabaseType<Boolean> {
    override fun extractFromSource(resultSet: ResultSet, index: Int): Boolean? {
        return resultSet.getNullable {
            resultSet.getBoolean(index)
        }
    }
    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean, ResultSet, PreparedStatement> = ::BooleanParameter
}
