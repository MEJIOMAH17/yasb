package com.github.mejiomah17.yasb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val generateTables: TaskProvider<GenerateTablesTask> =
            target.tasks.register("generateTables", GenerateTablesTask::class.java)
        target.afterEvaluate {
            if (target.tasks.withType(KotlinCompile::class.java).isNotEmpty()) {
                target.tasks.withType(KotlinCompile::class.java) {
                    it.dependsOn(generateTables)
                }
                target.extensions.findByType(KotlinProjectExtension::class.java)
                    ?.sourceSets
                    ?.named("main") {
                        it.kotlin.srcDir(generateTables.get().targetDir)
                    }
                try {
                    target.extensions.findByType(com.android.build.gradle.BaseExtension::class.java)
                        ?.sourceSets
                        ?.named("main") {
                            it.kotlin.srcDir(generateTables.get().targetDir)
                        }
                } catch (e: NoClassDefFoundError) {
                    // android plugin does not exist in classpath
                }
            }
        }
    }
}
