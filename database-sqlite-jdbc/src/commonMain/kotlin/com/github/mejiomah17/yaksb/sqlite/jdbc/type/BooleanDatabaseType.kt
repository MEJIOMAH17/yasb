package com.github.mejiomah17.yaksb.sqlite.jdbc.type

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.sqlite.jdbc.parameter.BooleanParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object BooleanDatabaseType : JDBCDatabaseType<Boolean> {
    override fun extractFromSource(
        source: ResultSet,
        index: Int,
    ): Boolean? =
        source.getNullable {
            source.getBoolean(index)
        }

    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean, ResultSet, PreparedStatement> = ::BooleanParameter
}
