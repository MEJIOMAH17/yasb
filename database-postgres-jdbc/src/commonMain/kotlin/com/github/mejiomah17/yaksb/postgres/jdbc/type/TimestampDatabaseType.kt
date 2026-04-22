package com.github.mejiomah17.yaksb.postgres.jdbc.type

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.core.parameter.TimestampParameter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

object TimestampDatabaseType : JDBCDatabaseType<Timestamp> {
    override fun extractFromSource(
        source: ResultSet,
        index: Int,
    ): Timestamp? = source.getTimestamp(index)

    override fun parameterFactory(): (Timestamp?) -> Parameter<Timestamp, ResultSet, PreparedStatement> = ::TimestampParameter
}
