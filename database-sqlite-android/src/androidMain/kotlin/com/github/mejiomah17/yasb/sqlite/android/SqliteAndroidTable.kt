package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.sqlite.SqliteTable
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.TextDatabaseType

interface SqliteAndroidTable<T : SqliteAndroidTable<T>> : SqliteTable<T, Cursor, (String) -> Unit> {
    override fun text(name: String): Column<T, String, Cursor, (String) -> Unit> {
        val register = register(Column(name, this as T, TextDatabaseType))
        return register
    }

    override fun textNullable(name: String): Column<T, String?, Cursor, (String) -> Unit> {
        return registerNullable(Column(name, this as T, TextDatabaseType))
    }

    override fun bool(name: String): Column<T, Boolean, Cursor, (String) -> Unit> {
        return register(Column(name, this as T, BooleanDatabaseType))
    }

    override fun boolNullable(name: String): Column<T, Boolean?, Cursor, (String) -> Unit> {
        return registerNullable(Column(name, this as T, BooleanDatabaseType))
    }

    override fun long(name: String): Column<T, Long, Cursor, (String) -> Unit> {
        return register(Column(name, this as T, LongDatabaseType))
    }

    override fun longNullable(name: String): Column<T, Long?, Cursor, (String) -> Unit> {
        return registerNullable(Column(name, this as T, LongDatabaseType))
    }
}
