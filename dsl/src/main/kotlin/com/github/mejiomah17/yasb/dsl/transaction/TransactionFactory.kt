package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import java.sql.Connection
import javax.sql.DataSource

abstract class TransactionFactory<D : DatabaseDialect>(
    private val datasource: DataSource
) {
    abstract fun dialect(): D

    fun <T> readUncommitted(
        block: context(D) TransactionReadUncommitted.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadUncommittedImpl(it) },
            jdbcLevel = TransactionReadUncommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> readCommitted(
        block: context(D) TransactionReadCommitted.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadCommittedImpl(it) },
            jdbcLevel = TransactionReadCommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> repeatableRead(
        block: context(D) TransactionRepeatableRead.() -> T
    ): T {
        return transaction(
            creator = { TransactionRepeatableReadImpl(it) },
            jdbcLevel = TransactionRepeatableRead.jdbcLevel,
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
