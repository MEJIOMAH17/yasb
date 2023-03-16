package com.github.mejiomah17.yasb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val generateTables: TaskProvider<GenerateTablesTask> =
            target.tasks.register("generateTables", GenerateTablesTask::class.java)
        target.afterEvaluate {
            target.tasks.withType(KotlinCompile::class.java) {
                it.dependsOn(generateTables)
            }
            if (target.tasks.withType(KotlinCompile::class.java).isNotEmpty()) {
                target.extensions.getByType(KotlinJvmProjectExtension::class.java)
                    .sourceSets
                    .named("main", KotlinSourceSet::class.java)
                    .get()
                    .kotlin.srcDir(generateTables.get().targetDir)
            }
        }
    }
}
