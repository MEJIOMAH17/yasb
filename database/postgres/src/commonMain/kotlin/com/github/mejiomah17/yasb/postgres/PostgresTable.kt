package com.github.mejiomah17.yasb.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import java.sql.Timestamp
import java.util.UUID

interface PostgresTable<TABLE : PostgresTable<TABLE, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE> :
    Table<TABLE, DRIVER_DATA_SOURCE> {
    fun text(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE>
    fun textNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE>
    fun bool(name: String): Column<TABLE, Boolean, DRIVER_DATA_SOURCE>

    fun boolNullable(name: String): Column<TABLE, Boolean?, DRIVER_DATA_SOURCE>

    fun uuid(name: String): Column<TABLE, UUID, DRIVER_DATA_SOURCE>

    fun uuidNullable(name: String): Column<TABLE, UUID?, DRIVER_DATA_SOURCE>

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<TABLE, Timestamp, DRIVER_DATA_SOURCE>

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<TABLE, Timestamp?, DRIVER_DATA_SOURCE>

    fun doublePrecision(name: String): Column<TABLE, Double, DRIVER_DATA_SOURCE>
    fun doublePrecisionNullable(name: String): Column<TABLE, Double?, DRIVER_DATA_SOURCE>

    fun bigint(name: String): Column<TABLE, Long, DRIVER_DATA_SOURCE>

    fun bigintNullable(name: String): Column<TABLE, Long?, DRIVER_DATA_SOURCE>

    fun jsonb(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE>

    fun jsonbNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE>
}
