package com.github.mejiomah17.yasb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.parameter.BlobParameter

object BlobDatabaseType : AndroidDatabaseType<ByteArray> {
    override fun parameterFactory(): (ByteArray?) -> Parameter<ByteArray, Cursor, AndroidSqliteDriverStatement> =
        ::BlobParameter

    override fun extractFromSource(source: Cursor, index: Int): ByteArray? {
        return source.getBlob(index)
    }
}
