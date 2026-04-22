package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.ddl.Column
import com.github.mejiomah17.yaksb.sqlite.SqliteTable
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.type.BlobDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.type.LongDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.type.TextDatabaseType

interface SqliteAndroidTable<T : SqliteAndroidTable<T>> : SqliteTable<T, Cursor, AndroidSqliteDriverStatement> {
    override fun text(name: String): Column<T, String, Cursor, AndroidSqliteDriverStatement> {
        val register = register(Column(name, this as T, TextDatabaseType))
        return register
    }

    override fun textNullable(name: String): Column<T, String?, Cursor, AndroidSqliteDriverStatement> =
        registerNullable(Column(name, this as T, TextDatabaseType))

    override fun bool(name: String): Column<T, Boolean, Cursor, AndroidSqliteDriverStatement> =
        register(Column(name, this as T, BooleanDatabaseType))

    override fun boolNullable(name: String): Column<T, Boolean?, Cursor, AndroidSqliteDriverStatement> =
        registerNullable(Column(name, this as T, BooleanDatabaseType))

    override fun blob(name: String): Column<T, ByteArray, Cursor, AndroidSqliteDriverStatement> =
        register(Column(name, this as T, BlobDatabaseType))

    override fun blobNullable(name: String): Column<T, ByteArray?, Cursor, AndroidSqliteDriverStatement> =
        registerNullable(Column(name, this as T, BlobDatabaseType))

    override fun long(name: String): Column<T, Long, Cursor, AndroidSqliteDriverStatement> =
        register(Column(name, this as T, LongDatabaseType))

    override fun longNullable(name: String): Column<T, Long?, Cursor, AndroidSqliteDriverStatement> =
        registerNullable(Column(name, this as T, LongDatabaseType))
}
