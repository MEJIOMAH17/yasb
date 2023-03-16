package com.github.mejiomah17.yasb

import Version
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import kotlin.random.Random

class GeneratorPluginTest {
    private val kotlinPluginDeclaration = """kotlin("jvm") version "${Version.kotlinVersion}""""
    private val yasbPluginDeclaration = """id("com.github.mejiomah17.yasb.gradle-plugin")"""

    @Test
    fun `does not fail on empty project`() {
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
    fun `does not fail when task has no configuration`() {
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

    @Test
    fun `generates table definition`() {
        val migrationsDir = createMigration()
        val packageName = (0..5).joinToString(".") {
            Random.nextString()
        }
        val projectDir = gradleDir(
            """
            import com.github.mejiomah17.yasb.GenerateTablesTask
            plugins {
               $yasbPluginDeclaration
               $kotlinPluginDeclaration
            }
            repositories{
                mavenLocal()
                mavenCentral()
            }
            dependencies {
                implementation("com.github.mejiomah17.yasb:dsl-postgres:${Version.yasbVersion}")
            }
            tasks.withType<GenerateTablesTask> {
                packageName = "$packageName"
                flywayMigrationDirs.add(File("${migrationsDir.root}"))
            }  
            """.trimIndent()
        )

        runBuild(projectDir)

        val generatedFile =
            projectDir.root.resolve("build/generated/yasb/${packageName.replace(".", "/")}/TestTable.kt")
        generatedFile.shouldExist()
        generatedFile.readText().shouldBe(
            """
                package $packageName

                object TestTable : com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable<TestTable> {
                    override val tableName = "test"
                    val a = textNullable("a")
                    val b = text("b")
                }
                
            """.trimIndent()
        )
    }

    @Test
    fun `build can use generated code`() {
        val migrationsDir = createMigration()
        val packageName = (0..5).joinToString(".") {
            Random.nextString()
        }
        val projectDir = gradleDir(
            """
            import com.github.mejiomah17.yasb.GenerateTablesTask
            plugins {
               $yasbPluginDeclaration
               $kotlinPluginDeclaration
            }
            repositories{
                mavenLocal()
                mavenCentral()
            }
            dependencies {
                implementation("com.github.mejiomah17.yasb:dsl-postgres:${Version.yasbVersion}")
            }
            tasks.withType<GenerateTablesTask> {
                packageName = "$packageName"
                flywayMigrationDirs.add(File("${migrationsDir.root}"))
            }
            """.trimIndent()
        ) {
            generateUsage(packageName)
        }

        runBuild(projectDir)

        projectDir.root.resolve("build/classes/kotlin/main/UsageKt.class").shouldExist()
    }

    private fun createMigration() = tempFolder {
        newFile("V001__test_table.sql").writeText(
            """
                      CREATE TABLE test(
                                   a text,
                                   b text NOT NULL
                                );
            """.trimIndent()
        )
    }

    private fun TemporaryFolder.generateUsage(packageName: String) {
        val sourceCodeDir = root.resolve("src/main/kotlin")
        sourceCodeDir.mkdirs()
        sourceCodeDir.resolve("Usage.kt").writeText(
            """
                            fun main(){
                              $packageName.TestTable.tableName
                            }
            """.trimIndent()
        )
    }

    private fun runBuild(dir: TemporaryFolder, command: String = "build") {
        GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(dir.root)
            .withArguments(command)
            .build()
    }

    private fun Random.nextString(length: Int = 10): String {
        val charPool = ('a'..'z') + ('A'..'Z')
        return (1..length)
            .map { nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")
    }

    private fun gradleDir(buildFile: String, block: TemporaryFolder.() -> Unit = {}): TemporaryFolder {
        return tempFolder {
            newFile("settings.gradle.kts").writeText("")
            newFile("build.gradle.kts").writeText(buildFile)
            block()
        }
    }

    private fun tempFolder(block: TemporaryFolder.() -> Unit): TemporaryFolder {
        val dir = TemporaryFolder()
        dir.create()
        dir.block()
        return dir
    }
}
