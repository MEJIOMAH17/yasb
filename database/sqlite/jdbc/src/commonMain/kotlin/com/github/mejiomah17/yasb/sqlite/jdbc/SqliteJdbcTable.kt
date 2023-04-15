package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.sqlite.SqliteTable
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.TextDatabaseType
import java.sql.ResultSet

interface SqliteJdbcTable<T : SqliteJdbcTable<T>> : SqliteTable<T, ResultSet> {
    override fun text(name: String): Column<T, String, ResultSet> {
        return register(Column(name, this as T, TextDatabaseType))
    }

    override fun textNullable(name: String): Column<T, String?, ResultSet> {
        return registerNullable(Column(name, this as T, TextDatabaseType))
    }

    override fun bool(name: String): Column<T, Boolean, ResultSet> {
        return register(Column(name, this as T, BooleanDatabaseType))
    }

    override fun boolNullable(name: String): Column<T, Boolean?, ResultSet> {
        return registerNullable(Column(name, this as T, BooleanDatabaseType))
    }

    override fun long(name: String): Column<T, Long, ResultSet> {
        return register(Column(name, this as T, LongDatabaseType))
    }

    override fun longNullable(name: String): Column<T, Long?, ResultSet> {
        return registerNullable(Column(name, this as T, LongDatabaseType))
    }
}
