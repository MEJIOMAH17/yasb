package com.github.mejiomah17.yasb

import com.github.mejiomah17.yasb.dsl.generator.PostgresColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.PostgresTableMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.TableMetadataFactory
import com.github.mejiomah17.yasb.generator.flyway.FlywayGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.testcontainers.utility.DockerImageName
import java.io.File

open class GenerateTablesTask : DefaultTask() {
    @Input
    protected var _imageName: String = DockerImageName.parse("postgres").asCanonicalNameString()
    fun imageName(value: DockerImageName) {
        _imageName = value.asCanonicalNameString()
    }

    fun imageName(): DockerImageName {
        return DockerImageName.parse(_imageName)
    }

    @Input
    var tableMetadataFactory: TableMetadataFactory = PostgresTableMetadataFactory(PostgresColumnMetadataFactory())

    @Input
    var packageName: String = "database"

    @Input
    @Optional
    var schemaPattern: String? = null

    @OutputDirectory
    var targetDir: File = File(project.buildDir.absoluteFile, "generated/yasb")

    @InputFiles
    var flywayMigrationDirs: MutableCollection<File> = mutableListOf()

    @Input
    var tablesFilter: (String) -> Boolean = { true }

    @TaskAction
    internal open fun run() {
        FlywayGenerator.generate(
            imageName = imageName(),
            tableMetadataFactory = tableMetadataFactory,
            targetDir = targetDir,
            packageName = packageName,
            schemaPattern = schemaPattern,
            locations = flywayMigrationDirs.map {
                "filesystem:${it.absolutePath}"
            },
            tablesFilter = tablesFilter
        )
    }
}

var GenerateTablesTask.imageName: DockerImageName
    get() {
        return imageName()
    }
    set(value) {
        imageName(value)
    }
