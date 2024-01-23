package com.github.mejiomah17.yasb.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import java.sql.Timestamp
import java.util.UUID

interface PostgresTable<TABLE : PostgresTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun text(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun textNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun varchar(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun varcharNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun bool(name: String): Column<TABLE, Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun boolNullable(name: String): Column<TABLE, Boolean?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun uuid(name: String): Column<TABLE, UUID, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun uuidNullable(name: String): Column<TABLE, UUID?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<TABLE, Timestamp, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<TABLE, Timestamp?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun doublePrecision(name: String): Column<TABLE, Double, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun doublePrecisionNullable(name: String): Column<TABLE, Double?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun bigint(name: String): Column<TABLE, Long, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun bigintNullable(name: String): Column<TABLE, Long?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun jsonb(name: String): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

    fun jsonbNullable(name: String): Column<TABLE, String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
