package com.github.mejiomah17.yasb

import Version
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import localProperties
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.random.Random

class GeneratorPluginAndroidTest {

    @Test
    fun `generates_table_definition`() {
        val packageName = (0..5).joinToString(".") {
            Random.nextString()
        }
        val projectDir = generateGradleDir(packageName)

        runBuild(projectDir)
        val generatedFile =
            projectDir.root.resolve("app/build/generated/yasb/${packageName.replace(".", "/")}/TestTable.kt")
        generatedFile.shouldExist()
        generatedFile.readText().shouldBe(
            """
                package $packageName

                object TestTable : com.github.mejiomah17.yasb.sqlite.android.SqliteAndroidTable<TestTable> {
                    override val tableName = "test"
                    val a = textNullable("a")
                    val b = text("b")
                }

            """.trimIndent()
        )
    }

    fun generateGradleDir(
        packageName: String,
        block: TemporaryFolder.() -> Unit = {}
    ): TemporaryFolder {
        return tempFolder {
            File(this::class.java.classLoader.getResource("sqlite/android").file)
                .copyRecursively(this.root)
            this.root.resolve("local.properties").writeText(localProperties)
            val buildFile = File(this.root, "app/build.gradle.kts")
            buildFile.writeText(
                buildFile.readText()
                    .replace("<placeholder_for_package_name>", packageName)
                    .replace("<placeholder_version>", Version.yasbVersion)
                    .replace("<placeholder_plugin_declaration>", yasbPluginDeclaration)
            )
            block()
        }
    }
}
