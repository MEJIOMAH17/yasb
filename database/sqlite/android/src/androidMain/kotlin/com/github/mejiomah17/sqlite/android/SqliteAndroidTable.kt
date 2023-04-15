package com.github.mejiomah17.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.sqlite.android.type.LongDatabaseType
import com.github.mejiomah17.sqlite.android.type.TextDatabaseType
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.sqlite.SqliteTable

interface SqliteAndroidTable<T : SqliteAndroidTable<T>> : SqliteTable<T, Cursor> {
    override fun text(name: String): Column<T, String, Cursor> {
        return register(Column(name, this as T, TextDatabaseType))
    }

    override fun textNullable(name: String): Column<T, String?, Cursor> {
        return registerNullable(Column(name, this as T, TextDatabaseType))
    }

    override fun bool(name: String): Column<T, Boolean, Cursor> {
        return register(Column(name, this as T, BooleanDatabaseType))
    }

    override fun boolNullable(name: String): Column<T, Boolean?, Cursor> {
        return registerNullable(Column(name, this as T, BooleanDatabaseType))
    }

    override fun long(name: String): Column<T, Long, Cursor> {
        return register(Column(name, this as T, LongDatabaseType))
    }

    override fun longNullable(name: String): Column<T, Long?, Cursor> {
        return registerNullable(Column(name, this as T, LongDatabaseType))
    }
}
