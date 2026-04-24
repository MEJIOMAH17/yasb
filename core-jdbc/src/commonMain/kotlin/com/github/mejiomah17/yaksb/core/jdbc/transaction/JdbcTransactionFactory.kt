package com.github.mejiomah17.yaksb.core.jdbc.transaction

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.Repeater
import com.github.mejiomah17.yaksb.core.Repeater.Companion.repeatOn
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource

// TODO handle sqlite isolation levels
abstract class JdbcTransactionFactory<D : DatabaseDialect<ResultSet, PreparedStatement>>(
    private val datasource: DataSource,
) {
    fun <V> readUncommitted(
        repeater: Repeater<V> = defaultRepeater(),
        block: context(D)
        JdbcTransactionReadUncommitted.() -> V,
    ): V =
        transaction(
            creator = { ImplJdbcTransactionReadUncommitted(it) },
            jdbcLevel = JdbcTransactionReadUncommitted.jdbcLevel,
            block = block,
            repeater = repeater,
        )

    fun <V> readCommitted(
        repeater: Repeater<V> = defaultRepeater(),
        block: context(D)
        JdbcTransactionReadCommitted.() -> V,
    ): V =
        transaction(
            creator = { ImplJdbcTransactionReadCommitted(it) },
            jdbcLevel = JdbcTransactionReadCommitted.jdbcLevel,
            block = block,
            repeater = repeater,
        )

    fun <V> repeatableRead(
        repeater: Repeater<V> = defaultRepeater(),
        block: context(D)
        JdbcTransactionRepeatableRead.() -> V,
    ): V =
        transaction(
            creator = { ImplJdbcTransactionRepeatableRead(it) },
            jdbcLevel = JdbcTransactionRepeatableRead.jdbcLevel,
            block = block,
            repeater = repeater,
        )

    fun <V> serializable(
        repeater: Repeater<V> = defaultRepeater(),
        block: context(D)
        JdbcTransactionSerializable.() -> V,
    ): V =
        transaction(
            creator = { JdbcTransactionSerializableImpl(it) },
            jdbcLevel = JdbcTransactionSerializable.jdbcValue,
            block = block,
            repeater = repeater,
        )

    fun <V> defaultRepeater(): Repeater<V> = repeatOn<V, SQLException>(3)

    abstract fun dialect(): D

    private fun <T : JdbcTransaction, R> transaction(
        creator: (Connection) -> T,
        jdbcLevel: Int,
        block: context(D)
        T.() -> R,
        repeater: Repeater<R>,
    ): R =
        repeater.repeat {
            datasource.connection.use { connection ->
                connection.transactionIsolation = jdbcLevel
                connection.autoCommit = false
                try {
                    val result =
                        dialect().run {
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
