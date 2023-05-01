package com.github.mejiomah17.yasb.core.transaction

import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.Rows
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.Insert
import com.github.mejiomah17.yasb.core.dsl.InsertWithReturn
import com.github.mejiomah17.yasb.core.dsl.SelectQuery
import com.github.mejiomah17.yasb.core.dsl.Update

interface Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    fun SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute(): List<Row>

    fun SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lazy(): Rows

    fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> Insert<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute()

    fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> Update<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute()

    fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> InsertWithReturn<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lazy(): Rows

    fun <TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> InsertWithReturn<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.execute(): List<Row> {
        return lazy().use { it.toList() }
    }
}

interface TransactionAtLeastReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

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
