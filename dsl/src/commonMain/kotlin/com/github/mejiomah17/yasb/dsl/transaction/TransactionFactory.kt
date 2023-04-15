package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import java.sql.Connection
import javax.sql.DataSource

abstract class TransactionFactory<D : DatabaseDialect<S>, S>(
    private val datasource: DataSource
) {
    abstract fun dialect(): D

    fun <T> readUncommitted(
        block: context(D) JdbcTransactionReadUncommitted.() -> T
    ): T {
        return transaction(
            creator = { ImplJdbcTransactionReadUncommitted(it) },
            jdbcLevel = JdbcTransactionReadUncommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> readCommitted(
        block: context(D) JdbcTransactionReadCommitted.() -> T
    ): T {
        return transaction(
            creator = { ImplJdbcTransactionReadCommitted(it) },
            jdbcLevel = JdbcTransactionReadCommitted.jdbcLevel,
            block = block
        )
    }

    fun <T> repeatableRead(
        block: context(D) JdbcTransactionRepeatableRead.() -> T
    ): T {
        return transaction(
            creator = { ImplJdbcTransactionRepeatableRead(it) },
            jdbcLevel = JdbcTransactionRepeatableRead.jdbcLevel,
            block = block
        )
    }

    fun <T> serializable(
        block: context(D) JdbcTransactionSerializable.() -> T
    ): T {
        return transaction(
            creator = { JdbcTransactionSerializableImpl(it) },
            jdbcLevel = JdbcTransactionSerializable.jdbcValue,
            block = block
        )
    }

    private fun <T : JdbcTransaction, R> transaction(
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
