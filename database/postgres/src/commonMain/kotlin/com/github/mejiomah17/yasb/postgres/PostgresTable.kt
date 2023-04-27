package com.github.mejiomah17.yasb.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import java.sql.Timestamp
import java.util.UUID

interface PostgresTable<T : PostgresTable<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> : Table<T, DRIVER_DATA_SOURCE> {
    fun text(name: String): Column<T, String, DRIVER_DATA_SOURCE>
    fun textNullable(name: String): Column<T, String?, DRIVER_DATA_SOURCE>
    fun bool(name: String): Column<T, Boolean, DRIVER_DATA_SOURCE>

    fun boolNullable(name: String): Column<T, Boolean?, DRIVER_DATA_SOURCE>

    fun uuid(name: String): Column<T, UUID, DRIVER_DATA_SOURCE>

    fun uuidNullable(name: String): Column<T, UUID?, DRIVER_DATA_SOURCE>

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<T, Timestamp, DRIVER_DATA_SOURCE>

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<T, Timestamp?, DRIVER_DATA_SOURCE>

    fun doublePrecision(name: String): Column<T, Double, DRIVER_DATA_SOURCE>
    fun doublePrecisionNullable(name: String): Column<T, Double?, DRIVER_DATA_SOURCE>

    fun bigint(name: String): Column<T, Long, DRIVER_DATA_SOURCE>

    fun bigintNullable(name: String): Column<T, Long?, DRIVER_DATA_SOURCE>

    fun jsonb(name: String): Column<T, String, DRIVER_DATA_SOURCE>

    fun jsonbNullable(name: String): Column<T, String?, DRIVER_DATA_SOURCE>
}
