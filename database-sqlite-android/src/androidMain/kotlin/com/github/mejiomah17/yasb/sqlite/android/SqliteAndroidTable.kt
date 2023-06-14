package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.sqlite.SqliteTable
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.type.BlobDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.TextDatabaseType

interface SqliteAndroidTable<T : SqliteAndroidTable<T>> : SqliteTable<T, Cursor, AndroidSqliteDriverStatement> {
    override fun text(name: String): Column<T, String, Cursor, AndroidSqliteDriverStatement> {
        val register = register(Column(name, this as T, TextDatabaseType))
        return register
    }

    override fun textNullable(name: String): Column<T, String?, Cursor, AndroidSqliteDriverStatement> {
        return registerNullable(Column(name, this as T, TextDatabaseType))
    }

    override fun bool(name: String): Column<T, Boolean, Cursor, AndroidSqliteDriverStatement> {
        return register(Column(name, this as T, BooleanDatabaseType))
    }

    override fun boolNullable(name: String): Column<T, Boolean?, Cursor, AndroidSqliteDriverStatement> {
        return registerNullable(Column(name, this as T, BooleanDatabaseType))
    }

    override fun blob(name: String): Column<T, ByteArray, Cursor, AndroidSqliteDriverStatement> {
        return register(Column(name, this as T, BlobDatabaseType))
    }

    override fun blobNullable(name: String): Column<T, ByteArray?, Cursor, AndroidSqliteDriverStatement> {
        return registerNullable(Column(name, this as T, BlobDatabaseType))
    }

    override fun long(name: String): Column<T, Long, Cursor, AndroidSqliteDriverStatement> {
        return register(Column(name, this as T, LongDatabaseType))
    }

    override fun longNullable(name: String): Column<T, Long?, Cursor, AndroidSqliteDriverStatement> {
        return registerNullable(Column(name, this as T, LongDatabaseType))
    }
}
