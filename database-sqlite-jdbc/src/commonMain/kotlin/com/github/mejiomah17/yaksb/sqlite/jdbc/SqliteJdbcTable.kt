package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.ddl.Column
import com.github.mejiomah17.yaksb.sqlite.SqliteTable
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.BlobDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.TextDatabaseType
import java.sql.PreparedStatement
import java.sql.ResultSet

interface SqliteJdbcTable<T : SqliteJdbcTable<T>> : SqliteTable<T, ResultSet, PreparedStatement> {
    override fun text(name: String): Column<T, String, ResultSet, PreparedStatement> = register(Column(name, this as T, TextDatabaseType))

    override fun textNullable(name: String): Column<T, String?, ResultSet, PreparedStatement> =
        registerNullable(Column(name, this as T, TextDatabaseType))

    override fun bool(name: String): Column<T, Boolean, ResultSet, PreparedStatement> =
        register(Column(name, this as T, BooleanDatabaseType))

    override fun boolNullable(name: String): Column<T, Boolean?, ResultSet, PreparedStatement> =
        registerNullable(Column(name, this as T, BooleanDatabaseType))

    override fun blob(name: String): Column<T, ByteArray, ResultSet, PreparedStatement> =
        register(Column(name, this as T, BlobDatabaseType))

    override fun blobNullable(name: String): Column<T, ByteArray?, ResultSet, PreparedStatement> =
        registerNullable(Column(name, this as T, BlobDatabaseType))

    override fun long(name: String): Column<T, Long, ResultSet, PreparedStatement> = register(Column(name, this as T, LongDatabaseType))

    override fun longNullable(name: String): Column<T, Long?, ResultSet, PreparedStatement> =
        registerNullable(Column(name, this as T, LongDatabaseType))
}
