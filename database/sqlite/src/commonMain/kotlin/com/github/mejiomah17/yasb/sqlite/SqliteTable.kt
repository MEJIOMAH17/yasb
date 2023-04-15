package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface SqliteTable<T : SqliteTable<T, S>, S> : Table<T, S> {
    fun text(name: String): Column<T, String, S>

    fun textNullable(name: String): Column<T, String?, S>

    fun bool(name: String): Column<T, Boolean, S>

    fun boolNullable(name: String): Column<T, Boolean?, S>

    fun long(name: String): Column<T, Long, S>
    fun longNullable(name: String): Column<T, Long?, S>
}
