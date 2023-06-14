package com.github.mejiomah17.yasb.sqlite.jdbc.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BlobDatabaseType
import java.sql.PreparedStatement

class BlobParameter(
    override val value: ByteArray?
) : SqliteParameter<ByteArray>() {
    override val databaseType: JDBCDatabaseType<ByteArray> = BlobDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setBytes(index, value)
    }
}
