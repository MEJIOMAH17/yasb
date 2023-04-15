package com.github.mejiomah17.yasb.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import java.sql.Timestamp
import java.util.UUID

interface PostgresTable<T : PostgresTable<T, S>, S> : Table<T, S> {
    fun text(name: String): Column<T, String, S>
    fun textNullable(name: String): Column<T, String?, S>
    fun bool(name: String): Column<T, Boolean, S>

    fun boolNullable(name: String): Column<T, Boolean?, S>

    fun uuid(name: String): Column<T, UUID, S>

    fun uuidNullable(name: String): Column<T, UUID?, S>

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<T, Timestamp, S>

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<T, Timestamp?, S>

    fun doublePrecision(name: String): Column<T, Double, S>
    fun doublePrecisionNullable(name: String): Column<T, Double?, S>

    fun bigint(name: String): Column<T, Long, S>

    fun bigintNullable(name: String): Column<T, Long?, S>

    fun jsonb(name: String): Column<T, String, S>

    fun jsonbNullable(name: String): Column<T, String?, S>
}
