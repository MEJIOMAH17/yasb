package com.github.mejiomah17.yasb

import com.github.mejiomah17.yasb.dsl.generator.TableMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.postgres.PostgresColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.postgres.PostgresTableMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.sqlite.SqliteColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.sqlite.SqliteTableMetadataFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.postgresql.Driver
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.io.Closeable
import java.io.Serializable
import java.sql.DriverManager
import javax.sql.DataSource

sealed class Database : Serializable {
    internal abstract fun datasource(): CloseableDataSource
    internal abstract val tableMetadataFactory: TableMetadataFactory

    class Postgres internal constructor(
        private val imageName: String = DockerImageName.parse("postgres").asCanonicalNameString(),
        override val tableMetadataFactory: PostgresTableMetadataFactory
    ) : Database() {
        constructor(
            imageName: DockerImageName = DockerImageName.parse("postgres"),
            tableMetadataFactory: PostgresTableMetadataFactory = PostgresTableMetadataFactory(
                PostgresColumnMetadataFactory()
            )
        ) : this(imageName.asCanonicalNameString(), tableMetadataFactory)

        override fun datasource(): CloseableDataSource {
            val container = PostgresContainer(DockerImageName.parse(imageName))
            container.start()
            DriverManager.registerDriver(Driver())
            return PostgresContainerDataSource(
                container = container,
                datasource = com.zaxxer.hikari.HikariDataSource(
                    com.zaxxer.hikari.HikariConfig().also {
                        it.jdbcUrl = container.jdbcUrl
                        it.username = PostgresContainer.LOGIN
                        it.password = PostgresContainer.PASSWORD
                    }
                )
            )
        }

        private class PostgresContainerDataSource(
            val container: PostgresContainer,
            val datasource: DataSource
        ) : DataSource by datasource, CloseableDataSource {
            override fun close() {
                container.close()
            }
        }

        class PostgresContainer(imageName: DockerImageName) : PostgreSQLContainer<PostgresContainer>(imageName) {
            init {
                this.withDatabaseName("test")
                    .withUsername(LOGIN)
                    .withPassword(PASSWORD)
            }

            companion object {
                val LOGIN = "postgres"
                val PASSWORD = "test"
            }
        }
    }

    class Sqlite(
        override val tableMetadataFactory: SqliteTableMetadataFactory = SqliteTableMetadataFactory(
            SqliteColumnMetadataFactory()
        )
    ) : Database() {
        override fun datasource(): CloseableDataSource {
            val datasource = HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = "jdbc:sqlite:"
                }
            )
            return object : DataSource by datasource, Closeable by datasource, CloseableDataSource {}
        }
    }
}
