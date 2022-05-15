package com.github.mejiomah17.yasb.generator.flyway

import com.github.mejiomah17.yasb.dsl.generator.ResultSetIterator
import com.github.mejiomah17.yasb.dsl.generator.TableGenerator
import com.github.mejiomah17.yasb.dsl.generator.TableMetadataFactory
import org.flywaydb.core.api.Location
import org.testcontainers.utility.DockerImageName
import java.io.File
//todo test
object FlywayGenerator {
    fun generate(
        imageName: DockerImageName,
        tableMetadataFactory: TableMetadataFactory,
        targetDir: File,
        packageName: String,
        schemaPattern: String?,
        locations: List<String>,
        tablesFilter: (String) -> Boolean
    ) {
        PostgresContainer(imageName).use { postgresContainer ->
            postgresContainer.start()
            val datasource = com.zaxxer.hikari.HikariDataSource(com.zaxxer.hikari.HikariConfig().also {
                it.jdbcUrl = postgresContainer.jdbcUrl
                it.username = PostgresContainer.LOGIN
                it.password = PostgresContainer.PASSWORD
            })
            // Create the Flyway instance and point it to the database
            val flyway: org.flywaydb.core.Flyway = org.flywaydb.core.Flyway.configure()
                .dataSource(datasource)
                .locations(*locations.toTypedArray())
                .load()

            // Start the migration
            flyway.migrate()
            datasource.connection.use { connection ->
                ResultSetIterator(
                    connection.metaData.getTables(
                        null, schemaPattern, null, arrayOf("TABLE")
                    )
                ).asSequence()
                    .map {
                        it.getString(3)
                    }.filter {
                        it != "flyway_schema_history"
                    }.filter(tablesFilter).map { tableName ->
                        val table = TableGenerator().generateTable(
                            tableMetadataFactory.create(
                                connection = connection,
                                tableName = tableName,
                                schemaPattern = schemaPattern
                            ), packageName
                        )
                        val dir = File(targetDir, packageName.replace(".", "/"))
                        dir.mkdirs()
                        File(dir, table.fileName).writeText(table.content)
                    }.count()

            }
            postgresContainer.jdbcUrl
        }
    }
}