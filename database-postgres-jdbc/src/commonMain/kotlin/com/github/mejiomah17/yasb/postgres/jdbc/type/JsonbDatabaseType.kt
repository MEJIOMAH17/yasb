package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.JsonbParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object JsonbDatabaseType : JDBCDatabaseType<String> {
    override fun extractFromSource(source: ResultSet, index: Int): String? {
        return source.getString(index)
    }

    override fun parameterFactory(): (String?) -> Parameter<String, ResultSet, PreparedStatement> = ::JsonbParameter
}
