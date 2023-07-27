package com.github.mejiomah17.yasb.sqlite.jdbc.type

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.BlobParameter
import java.sql.PreparedStatement
import java.sql.ResultSet

object BlobDatabaseType : JDBCDatabaseType<ByteArray> {
    override fun extractFromSource(resultSet: ResultSet, index: Int): ByteArray? {
        return resultSet.getNullable {
            resultSet.getBytes(index)
        }
    }

    override fun parameterFactory(): (ByteArray?) -> Parameter<ByteArray, ResultSet, PreparedStatement> =
        ::BlobParameter
}
