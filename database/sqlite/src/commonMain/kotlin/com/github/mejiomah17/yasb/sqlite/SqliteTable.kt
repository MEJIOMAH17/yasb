package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table

interface SqliteTable<T : SqliteTable<T>> : Table<T> {
    fun text(name: String): Column<T, String>

    fun textNullable(name: String): Column<T, String?>

    fun bool(name: String): Column<T, Boolean>

    fun boolNullable(name: String): Column<T, Boolean?>

    fun long(name: String): Column<T, Long>
    fun longNullable(name: String): Column<T, Long?>
}
