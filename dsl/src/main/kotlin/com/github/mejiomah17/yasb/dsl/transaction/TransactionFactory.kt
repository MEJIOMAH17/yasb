package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import java.sql.Connection
import javax.sql.DataSource

abstract class TransactionFactory(
    private val datasource: DataSource
) {
    protected abstract fun dialect(): DatabaseDialect

    fun <T> readUncommitted(
        block: context(DatabaseDialect) TransactionReadUncommitted.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadUncommittedImpl(it) },
            jdbcLevel = TransactionReadUncommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> readCommitted(
        block: context(DatabaseDialect) TransactionReadCommitted.() -> T
    ): T {
        return transaction(
            creator = { TransactionReadCommittedImpl(it) },
            jdbcLevel = TransactionReadCommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> repeatableRead(
        block: context(DatabaseDialect) TransactionRepeatableRead.() -> T
    ): T {
        return transaction(
            creator = { TransactionRepeatableReadImpl(it) },
            jdbcLevel = TransactionRepeatableRead.jdbcLevel,
            block = block
        )
    }

    fun <T> serializable(
        block: context(DatabaseDialect) TransactionSerializable.() -> T
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
        block: context(DatabaseDialect) T.() -> R
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
                throw e;
            }

        }
    }
}