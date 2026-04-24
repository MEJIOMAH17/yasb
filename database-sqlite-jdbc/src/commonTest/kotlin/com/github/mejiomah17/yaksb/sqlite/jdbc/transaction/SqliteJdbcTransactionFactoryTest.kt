package com.github.mejiomah17.yaksb.sqlite.jdbc.transaction

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionFactoryTest
import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionRepeatableRead
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.jdbc.SqliteJdbcTransactionFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class SqliteJdbcTransactionFactoryTest :
    JdbcTransactionFactoryTest<ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransactionRepeatableRead>() {
    override fun <V> transaction(block: context(SqliteJdbcDatabaseDialect) JdbcTransactionRepeatableRead.() -> V): V =
        SqliteJdbcTransactionFactory(dataSource).repeatableRead {
            block()
        }

    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeClass
        @JvmStatic
        fun init() {
            dataSource =
                HikariDataSource(
                    HikariConfig().also {
                        it.jdbcUrl = "jdbc:sqlite::memory:"
                    },
                )
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    override fun exception(): Exception = SQLException()
}
