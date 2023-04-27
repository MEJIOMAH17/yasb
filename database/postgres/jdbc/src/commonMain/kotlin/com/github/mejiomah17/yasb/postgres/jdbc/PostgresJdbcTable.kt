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

interface PostgresJdbcTable<TABLE : PostgresJdbcTable<TABLE>> : Table<TABLE, ResultSet> {
    fun text(name: String): Column<TABLE, String, ResultSet> {
        return register(Column(name, this as TABLE, TextDatabaseType))
    }

    fun textNullable(name: String): Column<TABLE, String?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, TextDatabaseType))
    }

    fun bool(name: String): Column<TABLE, Boolean, ResultSet> {
        return register(Column(name, this as TABLE, BooleanDatabaseType))
    }

    fun boolNullable(name: String): Column<TABLE, Boolean?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, BooleanDatabaseType))
    }

    fun uuid(name: String): Column<TABLE, UUID, ResultSet> {
        return register(Column(name, this as TABLE, UuidDatabaseType))
    }

    fun uuidNullable(name: String): Column<TABLE, UUID?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, UuidDatabaseType))
    }

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<TABLE, Timestamp, ResultSet> {
        return register(Column(name, this as TABLE, TimestampDatabaseType))
    }

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<TABLE, Timestamp?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, TimestampDatabaseType))
    }

    fun doublePrecision(name: String): Column<TABLE, Double, ResultSet> {
        return register(Column(name, this as TABLE, DoublePrecisionDatabaseType))
    }

    fun doublePrecisionNullable(name: String): Column<TABLE, Double?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, DoublePrecisionDatabaseType))
    }

    fun bigint(name: String): Column<TABLE, Long, ResultSet> {
        return register(Column(name, this as TABLE, LongDatabaseType))
    }

    fun bigintNullable(name: String): Column<TABLE, Long?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, LongDatabaseType))
    }

    fun jsonb(name: String): Column<TABLE, String, ResultSet> {
        return register(Column(name, this as TABLE, JsonbDatabaseType))
    }

    fun jsonbNullable(name: String): Column<TABLE, String?, ResultSet> {
        return registerNullable(Column(name, this as TABLE, JsonbDatabaseType))
    }
}
