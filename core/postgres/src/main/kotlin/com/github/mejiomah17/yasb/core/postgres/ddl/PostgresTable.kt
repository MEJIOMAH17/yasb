package com.github.mejiomah17.yasb.core.postgres.ddl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.postgres.type.TextDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TextNullableDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TimestampNullableDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.UuidDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.UuidNullableDatabaseType
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

interface PostgresTable<T : PostgresTable<T>> : Table<T> {
    fun text(name: String): Column<T, String> {
        return register(Column(name, this, TextDatabaseType))
    }

    fun textNullable(name: String): Column<T, String?> {
        return register(Column(name, this, TextNullableDatabaseType))
    }

    fun uuid(name: String): Column<T, UUID> {
        return register(Column(name, this, UuidDatabaseType))
    }

    fun uuidNullable(name: String): Column<T, UUID?> {
        return register(Column(name, this, UuidNullableDatabaseType))
    }

    fun timestamp(name: String): Column<T, Timestamp> {
        return register(Column(name, this, TimestampDatabaseType))
    }

    fun timestampNullable(name: String): Column<T, Timestamp?> {
        return register(Column(name, this, TimestampNullableDatabaseType))
    }

}

