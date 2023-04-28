@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect

interface TransactionFactory<
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > {

    fun dialect(): DIALECT

    fun <V> readUncommitted(
        block: context(DIALECT, TransactionReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) TRANSACTION.() -> V
    ): V

    fun <V> readCommitted(
        block: context(DIALECT, TransactionReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) TRANSACTION.() -> V
    ): V

    fun <V> repeatableRead(
        block: context(DIALECT, TransactionRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) TRANSACTION.() -> V
    ): V

    fun <V> serializable(
        block: context(DIALECT, TransactionSerializable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>) TRANSACTION.() -> V
    ): V
}
