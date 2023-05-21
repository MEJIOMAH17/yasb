package com.github.mejiomah17.yasb

import Version
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder

class GeneratorPluginTest {
    private val kotlinPluginDeclaration = """kotlin("jvm") version "${Version.kotlinVersion}""""
    @Test
    fun `does_not_fail_on_empty_project`() {
        val projectDir = gradleDir(
            """
             plugins {
                 $yasbPluginDeclaration
             }
            """.trimIndent()
        )

        runBuild(projectDir)
    }

    @Test
    fun `does_not_fail_when_task_has_no_configuration`() {
        val projectDir = gradleDir(
            """
            plugins {
               $yasbPluginDeclaration
               $kotlinPluginDeclaration
            }
            """.trimIndent()
        )

        runBuild(projectDir)
    }

    private fun runBuild(dir: TemporaryFolder, command: String = "build") {
        GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(dir.root)
            .withArguments(command)
            .build()
    }

    private fun gradleDir(buildFile: String, block: TemporaryFolder.() -> Unit = {}): TemporaryFolder {
        return tempFolder {
            newFile("settings.gradle.kts").writeText("")
            newFile("build.gradle.kts").writeText(buildFile)
            block()
        }
    }
}
