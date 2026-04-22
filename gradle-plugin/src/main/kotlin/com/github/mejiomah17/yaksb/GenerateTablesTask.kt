package com.github.mejiomah17.yaksb

import com.github.mejiomah17.yaksb.generator.flyway.FlywayGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

@CacheableTask
open class GenerateTablesTask : DefaultTask() {
    @Input
    @Optional
    var database: Database? = null

    @Input
    var packageName: String = "database"

    @Input
    @Optional
    var schemaPattern: String? = null

    @OutputDirectory
    var targetDir: File = File(project.layout.buildDirectory.asFile.get().absoluteFile, "generated/yaksb")

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    var flywayMigrationDirs: MutableCollection<File> = mutableListOf()

    @Input
    var tablesFilter: (String) -> Boolean = { true }

    @TaskAction
    internal open fun run() {
        val db = database
        if (db != null) {
            db.datasource().use { datasource ->
                FlywayGenerator.generate(
                    datasource = datasource,
                    tableMetadataFactory = db.tableMetadataFactory,
                    targetDir = targetDir,
                    packageName = packageName,
                    schemaPattern = schemaPattern,
                    locations = flywayMigrationDirs.map {
                        "filesystem:${it.absolutePath}"
                    },
                    tablesFilter = tablesFilter
                )
            }
        } else {
            logger.warn("${this::database.name} property is null. Task do not do anything")
        }
    }
}
