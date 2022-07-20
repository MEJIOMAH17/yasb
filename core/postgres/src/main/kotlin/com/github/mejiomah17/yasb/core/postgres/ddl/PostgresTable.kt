package com.github.mejiomah17.yasb.core.postgres.ddl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.postgres.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.DoublePrecisionDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.JsonbDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.LongDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TextDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.TimestampDatabaseType
import com.github.mejiomah17.yasb.core.postgres.type.UuidDatabaseType
import java.sql.Timestamp
import java.util.*

interface PostgresTable<T : PostgresTable<T>> : Table<T> {
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

    fun uuid(name: String): Column<T, UUID> {
        return register(Column(name, this, UuidDatabaseType))
    }

    fun uuidNullable(name: String): Column<T, UUID?> {
        return registerNullable(Column(name, this, UuidDatabaseType))
    }

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<T, Timestamp> {
        return register(Column(name, this, TimestampDatabaseType))
    }

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<T, Timestamp?> {
        return registerNullable(Column(name, this, TimestampDatabaseType))
    }

    fun doublePrecision(name: String): Column<T, Double> {
        return register(Column(name, this, DoublePrecisionDatabaseType))
    }

    fun doublePrecisionNullable(name: String): Column<T, Double?> {
        return registerNullable(Column(name, this, DoublePrecisionDatabaseType))
    }

    fun bigint(name: String): Column<T, Long> {
        return register(Column(name, this, LongDatabaseType))
    }

    fun bigintNullable(name: String): Column<T, Long?> {
        return registerNullable(Column(name, this, LongDatabaseType))
    }

    fun jsonb(name: String): Column<T, String> {
        return register(Column(name, this, JsonbDatabaseType))
    }

    fun jsonbNullable(name: String): Column<T, String?> {
        return registerNullable(Column(name, this, JsonbDatabaseType))
    }
}

