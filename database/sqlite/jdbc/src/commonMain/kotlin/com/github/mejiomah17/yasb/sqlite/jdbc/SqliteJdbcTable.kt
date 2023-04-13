package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.sqlite.SqliteTable
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.TextDatabaseType

interface SqliteJdbcTable<T : SqliteJdbcTable<T>> : SqliteTable<T> {
    override fun text(name: String): Column<T, String> {
        return register(Column(name, this, TextDatabaseType))
    }

    override fun textNullable(name: String): Column<T, String?> {
        return registerNullable(Column(name, this, TextDatabaseType))
    }

    override fun bool(name: String): Column<T, Boolean> {
        return register(Column(name, this, BooleanDatabaseType))
    }

    override fun boolNullable(name: String): Column<T, Boolean?> {
        return registerNullable(Column(name, this, BooleanDatabaseType))
    }

    override fun long(name: String): Column<T, Long> {
        return register(Column(name, this, LongDatabaseType))
    }
    override fun longNullable(name: String): Column<T, Long?> {
        return registerNullable(Column(name, this, LongDatabaseType))
    }
}