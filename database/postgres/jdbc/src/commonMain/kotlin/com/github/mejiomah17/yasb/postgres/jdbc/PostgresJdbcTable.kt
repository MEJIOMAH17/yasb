package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.postgres.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.DoublePrecisionDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.JsonbDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.TextDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.TimestampDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.UuidDatabaseType
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.UUID

interface PostgresJdbcTable<T : PostgresJdbcTable<T>> : Table<T, ResultSet> {
    fun text(name: String): Column<T, String, ResultSet> {
        return register(Column(name, this as T, TextDatabaseType))
    }

    fun textNullable(name: String): Column<T, String?, ResultSet> {
        return registerNullable(Column(name, this as T, TextDatabaseType))
    }

    fun bool(name: String): Column<T, Boolean, ResultSet> {
        return register(Column(name, this as T, BooleanDatabaseType))
    }

    fun boolNullable(name: String): Column<T, Boolean?, ResultSet> {
        return registerNullable(Column(name, this as T, BooleanDatabaseType))
    }

    fun uuid(name: String): Column<T, UUID, ResultSet> {
        return register(Column(name, this as T, UuidDatabaseType))
    }

    fun uuidNullable(name: String): Column<T, UUID?, ResultSet> {
        return registerNullable(Column(name, this as T, UuidDatabaseType))
    }

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<T, Timestamp, ResultSet> {
        return register(Column(name, this as T, TimestampDatabaseType))
    }

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<T, Timestamp?, ResultSet> {
        return registerNullable(Column(name, this as T, TimestampDatabaseType))
    }

    fun doublePrecision(name: String): Column<T, Double, ResultSet> {
        return register(Column(name, this as T, DoublePrecisionDatabaseType))
    }

    fun doublePrecisionNullable(name: String): Column<T, Double?, ResultSet> {
        return registerNullable(Column(name, this as T, DoublePrecisionDatabaseType))
    }

    fun bigint(name: String): Column<T, Long, ResultSet> {
        return register(Column(name, this as T, LongDatabaseType))
    }

    fun bigintNullable(name: String): Column<T, Long?, ResultSet> {
        return registerNullable(Column(name, this as T, LongDatabaseType))
    }

    fun jsonb(name: String): Column<T, String, ResultSet> {
        return register(Column(name, this as T, JsonbDatabaseType))
    }

    fun jsonbNullable(name: String): Column<T, String?, ResultSet> {
        return registerNullable(Column(name, this as T, JsonbDatabaseType))
    }
}
