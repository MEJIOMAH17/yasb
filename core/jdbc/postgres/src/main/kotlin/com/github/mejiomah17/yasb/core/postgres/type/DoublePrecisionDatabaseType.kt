package com.github.mejiomah17.yasb.core.postgres.type

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.DoubleParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object DoublePrecisionDatabaseType : DatabaseType<Double> {
    override fun extractFromResultSet(resultSet: ResultSet, index: Int): Double? {
        return resultSet.getNullable {
            resultSet.getDouble(index)
        }
    }

    override fun applyParameterToStatement(parameter: Parameter<Double>, statement: PreparedStatement, index: Int) {
        statement.setObject(index, parameter.value)
    }

    override fun parameterFactory(): (Double?) -> Parameter<Double> = ::DoubleParameter
}
