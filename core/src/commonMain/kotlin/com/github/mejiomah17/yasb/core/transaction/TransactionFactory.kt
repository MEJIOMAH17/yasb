@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect

interface TransactionFactory<
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION_READ_UNCOMMITTED : TransactionAtLeastReadUncommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION_READ_COMMITTED : TransactionAtLeastReadCommitted<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION_REPEATABLE_READ : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION_SERIALIZABLE : TransactionAtLeastSerializable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > {

    fun dialect(): DIALECT

    fun <V> readUncommitted(
        block: context(DIALECT) TRANSACTION_READ_UNCOMMITTED.() -> V
    ): V

    fun <V> readCommitted(
        block: context(DIALECT) TRANSACTION_READ_COMMITTED.() -> V
    ): V

    fun <V> repeatableRead(
        block: context(DIALECT) TRANSACTION_REPEATABLE_READ.() -> V
    ): V

    fun <V> serializable(
        block: context(DIALECT) TRANSACTION_SERIALIZABLE.() -> V
    ): V
}
