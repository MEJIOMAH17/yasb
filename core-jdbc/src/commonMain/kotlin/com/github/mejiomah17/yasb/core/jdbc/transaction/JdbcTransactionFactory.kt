@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

abstract class JdbcTransactionFactory<D : DatabaseDialect<ResultSet, PreparedStatement>>(
    private val datasource: DataSource
) : TransactionFactory<
        ResultSet,
        PreparedStatement,
        D,
        JdbcTransactionReadUncommitted,
        JdbcTransactionReadCommitted,
        JdbcTransactionRepeatableRead,
        JdbcTransactionSerializable
        > {

    override fun <V> readUncommitted(
        block: context(D) JdbcTransactionReadUncommitted.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionReadUncommitted(it) },
            jdbcLevel = JdbcTransactionReadUncommitted.jdbcLevel,
            block = block
        )
    }

    override fun <V> readCommitted(
        block: context(D) JdbcTransactionReadCommitted.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionReadCommitted(it) },
            jdbcLevel = JdbcTransactionReadCommitted.jdbcLevel,
            block = block
        )
    }

    override fun <V> repeatableRead(
        block: context(D) JdbcTransactionRepeatableRead.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionRepeatableRead(it) },
            jdbcLevel = JdbcTransactionRepeatableRead.jdbcLevel,
            block = block
        )
    }

    override fun <V> serializable(
        block: context(D) JdbcTransactionSerializable.() -> V
    ): V {
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
                    block(dialect(), creator(connection))
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
