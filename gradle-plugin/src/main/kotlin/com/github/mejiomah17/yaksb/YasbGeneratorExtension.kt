package com.github.mejiomah17.yaksb

import com.github.mejiomah17.yaksb.dsl.generator.TableMetadataFactory
import com.github.mejiomah17.yaksb.postgres.jdbc.generator.PostgresColumnMetadataFactory
import com.github.mejiomah17.yaksb.postgres.jdbc.generator.PostgresTableMetadataFactory
import org.gradle.api.Project
import org.testcontainers.utility.DockerImageName
import java.io.File

class YasbGeneratorExtension(
    project: Project,
) {
    var imageName: DockerImageName = DockerImageName.parse("postgres")
    var tableMetadataFactory: TableMetadataFactory = PostgresTableMetadataFactory(PostgresColumnMetadataFactory())
    var packageName: String = "database"
    var schemaPattern: String? = null
    var targetDir: File = File(project.buildDir.absoluteFile, "generated/yaksb")
    var flywayMigrationDirs: MutableCollection<File> = mutableListOf()
    var tablesFilter: (String) -> Boolean = { true }
}
