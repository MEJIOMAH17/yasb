package com.github.mejiomah17.yasb.postgres.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.DoubleParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object DoublePrecisionDatabaseType : JDBCDatabaseType<Double> {
    override fun extractFromSource(source: ResultSet, index: Int): Double? {
        return source.getNullable {
            source.getDouble(index)
        }
    }

    override fun parameterFactory(): (Double?) -> Parameter<Double, ResultSet, PreparedStatement> = ::DoubleParameter
}
