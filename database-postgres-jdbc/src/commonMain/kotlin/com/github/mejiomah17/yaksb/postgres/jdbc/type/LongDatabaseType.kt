package com.github.mejiomah17.yaksb.postgres.jdbc.type

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.postgres.jdbc.parameter.LongParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object LongDatabaseType : JDBCDatabaseType<Long> {
    override fun extractFromSource(
        source: ResultSet,
        index: Int,
    ): Long? =
        source.getNullable {
            source.getLong(index)
        }

    override fun parameterFactory(): (Long?) -> Parameter<Long, ResultSet, PreparedStatement> = ::LongParameter
}
