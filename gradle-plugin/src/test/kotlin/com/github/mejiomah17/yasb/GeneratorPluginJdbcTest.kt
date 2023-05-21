package com.github.mejiomah17.yasb

import Version
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.random.Random

class GeneratorPluginJdbcTest {
    private val kotlinPluginDeclaration = """kotlin("jvm") version "${Version.kotlinVersion}""""

    @ParameterizedTest
    @EnumSource(DB::class)
    fun `generates_table_definition`(db: DB) {
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
                implementation("com.github.mejiomah17.yasb:database-postgres-jdbc-jvm:${Version.yasbVersion}")
                implementation("com.github.mejiomah17.yasb:database-sqlite-jdbc-jvm:${Version.yasbVersion}")
            }
            tasks.withType<GenerateTablesTask> {
                database = ${db.gradleDeclaration}
                packageName = "$packageName"
                flywayMigrationDirs.add(File("${migrationsDir.root.escapedPath()}"))
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

                object TestTable : ${db.tableSuperClass}<TestTable> {
                    override val tableName = "test"
                    val a = textNullable("a")
                    val b = text("b")
                }
                
            """.trimIndent()
        )
    }

    @ParameterizedTest
    @EnumSource(DB::class)
    fun `build_can_use_generated_code`(db: DB) {
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
                implementation("com.github.mejiomah17.yasb:database-postgres-jdbc-jvm:${Version.yasbVersion}")
                implementation("com.github.mejiomah17.yasb:database-sqlite-jdbc-jvm:${Version.yasbVersion}")
            }
            tasks.withType<GenerateTablesTask> {
                database = ${db.gradleDeclaration}
                packageName = "$packageName"
                flywayMigrationDirs.add(File("${migrationsDir.root.escapedPath()}"))
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

    private fun File.escapedPath(): String {
        return if (System.getProperty("os.name").lowercase().contains("windows")) {
            path.replace("\\", "/")
        } else {
            path
        }
    }

    enum class DB(val gradleDeclaration: String, val tableSuperClass: String) {
        Postgres(
            "com.github.mejiomah17.yasb.Database.Postgres()",
            "com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTable"
        ),
        Sqlite(
            "com.github.mejiomah17.yasb.Database.SqliteJdbc()",
            "com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable"
        ),
    }

    fun runBuild(dir: TemporaryFolder, command: String = "build") {
        GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(dir.root)
            .withArguments(command, "--stacktrace")
            .build()
    }
}
