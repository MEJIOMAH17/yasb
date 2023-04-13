package com.github.mejiomah17.yasb.postgres

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import java.sql.Timestamp
import java.util.UUID

interface PostgresTable<T : PostgresTable<T>> : Table<T> {
    fun text(name: String): Column<T, String>
    fun textNullable(name: String): Column<T, String?>
    fun bool(name: String): Column<T, Boolean>

    fun boolNullable(name: String): Column<T, Boolean?>

    fun uuid(name: String): Column<T, UUID>

    fun uuidNullable(name: String): Column<T, UUID?>

    /**
     * Register column for Timestamp type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestamp(name: String): Column<T, Timestamp>

    /**
     * Register column for Timestamp? type.
     * Attention! All nanosecond will be erased at insert statement.
     */
    fun timestampNullable(name: String): Column<T, Timestamp?>

    fun doublePrecision(name: String): Column<T, Double>
    fun doublePrecisionNullable(name: String): Column<T, Double?>

    fun bigint(name: String): Column<T, Long>

    fun bigintNullable(name: String): Column<T, Long?>

    fun jsonb(name: String): Column<T, String>

    fun jsonbNullable(name: String): Column<T, String?>
}
