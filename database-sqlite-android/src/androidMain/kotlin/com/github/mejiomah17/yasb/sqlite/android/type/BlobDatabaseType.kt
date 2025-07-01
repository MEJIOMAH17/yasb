package com.github.mejiomah17.yaksb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.parameter.BlobParameter

object BlobDatabaseType : AndroidDatabaseType<ByteArray> {
    override fun parameterFactory(): (ByteArray?) -> Parameter<ByteArray, Cursor, AndroidSqliteDriverStatement> = ::BlobParameter

    override fun extractFromSource(
        source: Cursor,
        index: Int,
    ): ByteArray? {
        return source.getBlob(index)
    }
}
