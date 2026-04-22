package com.github.mejiomah17.yaksb.sqlite.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.BlobDatabaseType
import java.sql.PreparedStatement

class BlobParameter(
    override val value: ByteArray?,
) : SqliteParameter<ByteArray>() {
    override val databaseType: JDBCDatabaseType<ByteArray> = BlobDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int,
    ) {
        statement.setBytes(index, value)
    }
}
