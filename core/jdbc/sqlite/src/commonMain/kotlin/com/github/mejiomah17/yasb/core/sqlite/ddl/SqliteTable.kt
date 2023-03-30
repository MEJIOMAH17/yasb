package com.github.mejiomah17.yasb.core.sqlite.ddl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.sqlite.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.sqlite.type.TextDatabaseType

interface SqliteTable<T : SqliteTable<T>> : Table<T> {
    fun text(name: String): Column<T, String> {
        return register(Column(name, this, TextDatabaseType))
    }

    fun textNullable(name: String): Column<T, String?> {
        return registerNullable(Column(name, this, TextDatabaseType))
    }

    fun bool(name: String): Column<T, Boolean> {
        return register(Column(name, this, BooleanDatabaseType))
    }

    fun boolNullable(name: String): Column<T, Boolean?> {
        return registerNullable(Column(name, this, BooleanDatabaseType))
    }
}
