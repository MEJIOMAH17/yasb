package com.github.mejiomah17.yasb.sqlite.android.parameter

import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.BlobDatabaseType

class BlobParameter(
    override val value: ByteArray?
) : SqliteParameter<ByteArray>() {
    override val databaseType: AndroidDatabaseType<ByteArray> = BlobDatabaseType
    override fun applyToStatement(statement: AndroidSqliteDriverStatement, index: Int) {
        if (value == null) {
            statement.bindNull(index)
        } else {
            statement.bindBlob(index, value)
        }
    }
}
