@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.Repeater
import com.github.mejiomah17.yasb.core.Repeater.Companion.repeatOn
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
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
        repeater: Repeater<V>,
        block: context(D) JdbcTransactionReadUncommitted.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionReadUncommitted(it) },
            jdbcLevel = JdbcTransactionReadUncommitted.jdbcLevel,
            block = block,
            repeater = repeater
        )
    }

    override fun <V> readCommitted(
        repeater: Repeater<V>,
        block: context(D) JdbcTransactionReadCommitted.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionReadCommitted(it) },
            jdbcLevel = JdbcTransactionReadCommitted.jdbcLevel,
            block = block,
            repeater = repeater
        )
    }

    override fun <V> repeatableRead(
        repeater: Repeater<V>,
        block: context(D) JdbcTransactionRepeatableRead.() -> V
    ): V {
        return transaction(
            creator = { ImplJdbcTransactionRepeatableRead(it) },
            jdbcLevel = JdbcTransactionRepeatableRead.jdbcLevel,
            block = block,
            repeater = repeater
        )
    }

    override fun <V> serializable(
        repeater: Repeater<V>,
        block: context(D) JdbcTransactionSerializable.() -> V
    ): V {
        return transaction(
            creator = { JdbcTransactionSerializableImpl(it) },
            jdbcLevel = JdbcTransactionSerializable.jdbcValue,
            block = block,
            repeater = repeater
        )
    }

    override fun <V> defaultRepeater(): Repeater<V> {
        return repeatOn<V, SQLException>(3)
    }

    private fun <T : JdbcTransaction, R> transaction(
        creator: (Connection) -> T,
        jdbcLevel: Int,
        block: context(D) T.() -> R,
        repeater: Repeater<R>
    ): R = repeater.repeat {
        datasource.connection.use { connection ->
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
