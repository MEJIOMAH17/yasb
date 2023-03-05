package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import java.sql.Connection
import javax.sql.DataSource

abstract class TransactionFactory<D : DatabaseDialect>(
    private val datasource: DataSource
) {
    abstract fun dialect(): D

    fun <T> readUncommitted(
        block: context(D) TransactionReadUncommittedTransaction.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadUncommittedImplTransaction(it) },
            jdbcLevel = TransactionReadUncommittedTransaction.jdbcLevel,
            block = block
        )
    }

    fun <T> readCommitted(
        block: context(D) TransactionReadCommittedTransaction.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadCommittedImplTransaction(it) },
            jdbcLevel = TransactionReadCommittedTransaction.jdbcLevel,
            block = block
        )
    }

    fun <T> repeatableRead(
        block: context(D) TransactionRepeatableReadTransaction.() -> T
    ): T {
        return transaction(
            creator = { TransactionRepeatableReadImplTransaction(it) },
            jdbcLevel = TransactionRepeatableReadTransaction.jdbcLevel,
            block = block
        )
    }

    fun <T> serializable(
        block: context(D) TransactionSerializable.() -> T
    ): T {
        return transaction(
            creator = { TransactionSerializableImpl(it) },
            jdbcLevel = TransactionSerializable.jdbcValue,
            block = block
        )
    }

    private fun <T : Transaction, R> transaction(
        creator: (Connection) -> T,
        jdbcLevel: Int,
        block: context(D) T.() -> R
    ): R {
        // TODO retry
        return datasource.connection.use { connection ->
            connection.transactionIsolation = jdbcLevel
            connection.autoCommit = false
            try {
                val result = dialect().run {
                    block(creator(connection))
                }
                connection.commit()
                result
            } catch (e: Exception) {
                connection.rollback()
                throw e
            }
        }
    }
}
