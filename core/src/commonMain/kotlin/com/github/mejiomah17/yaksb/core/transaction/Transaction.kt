package com.github.mejiomah17.yaksb.core.transaction

import com.github.mejiomah17.yaksb.core.Row
import com.github.mejiomah17.yaksb.core.Rows
import com.github.mejiomah17.yaksb.core.query.Query
import com.github.mejiomah17.yaksb.core.query.ReturningQuery

interface Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute()

    fun ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute(): List<Row> = lazy().use { it.toList() }

    fun ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lazy(): Rows
}

context(t: Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute() =
    with(t) {
        execute()
    }

context(t: Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute(): List<Row> =
    lazy().use {
        it.toList()
    }

context(t: Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lazy(): Rows =
    with(t) {
        lazy()
    }

interface TransactionAtLeastReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionAtLeastReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionAtLeastSerializable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface TransactionSerializable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    TransactionAtLeastSerializable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
