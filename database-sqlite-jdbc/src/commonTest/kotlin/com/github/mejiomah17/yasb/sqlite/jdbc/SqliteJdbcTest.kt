package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.TestTable
import com.github.mejiomah17.yasb.sqlite.jdbc.parameter.TextParameter
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.AfterClass
import org.junit.BeforeClass
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID
import kotlin.io.path.createTempFile
import kotlin.io.path.pathString

abstract class SqliteJdbcTest {
    companion object {
        lateinit var dataSource: HikariDataSource

        @BeforeClass
        @JvmStatic
        fun init() {
            val tmpFile = createTempFile(
                UUID.randomUUID().toString()
            ).pathString
            dataSource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite:file:$tmpFile"
                }
            )
            dataSource.connection.use {
                it.createStatement().use {
                    it.executeUpdate(
                        """
                            CREATE TABLE test(
                               a string DEFAULT NULL,
                               b string DEFAULT NULL,
                               c bigint DEFAULT NULL,
                               d boolean DEFAULT NULL,
                               e blob DEFAULT NULL
                            )
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE FIRST(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE SECOND(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                    it.execute(
                        """
                            CREATE TABLE THIRD(
                               A string,
                               B string
                            );
                        """.trimIndent()
                    )
                }
            }
        }

        @AfterClass
        @JvmStatic
        fun close() {
            kotlin.runCatching { dataSource.close() }
        }
    }

    fun executeSql(sql: String) {
        dataSource.connection.use {
            it.createStatement().use { statement ->
                statement.execute(sql)
            }
        }
    }

    fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    fun tableTest(): SqliteJdbcTestTable {
        return SqliteJdbcTestTable
    }

    fun transactionFactory(): SqliteJdbcTransactionFactory {
        return SqliteJdbcTransactionFactory(dataSource)
    }

    fun firstTable() = FirstTable

    fun secondTable() = SecondTable

    fun thirdTable() = ThirdTable

    fun dialect(): SqliteJdbcDatabaseDialect = SqliteJdbcDatabaseDialect

    object FirstTable : SqliteJdbcTable<FirstTable>, TestTable<FirstTable, ResultSet, PreparedStatement> {
        override val tableName: String = "FIRST"
        override val a = text("A")
        override val b = text("B")
    }

    object SecondTable : SqliteJdbcTable<SecondTable>, TestTable<SecondTable, ResultSet, PreparedStatement> {
        override val tableName: String = "SECOND"
        override val a = text("A")
        override val b = text("B")
    }

    object ThirdTable : SqliteJdbcTable<ThirdTable>, TestTable<ThirdTable, ResultSet, PreparedStatement> {
        override val tableName: String = "THIRD"
        override val a = text("A")
        override val b = text("B")
    }
}
