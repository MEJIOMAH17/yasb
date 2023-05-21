package com.github.mejiomah17.yasb

import org.gradle.testkit.runner.GradleRunner
import org.junit.rules.TemporaryFolder
import kotlin.random.Random

fun tempFolder(block: TemporaryFolder.() -> Unit): TemporaryFolder {
    val dir = TemporaryFolder()
    dir.create()
    dir.block()
    return dir
}

fun createMigration() = tempFolder {
    newFile("V001__test_table.sql").writeText(
        """
                      CREATE TABLE test(
                                   a text,
                                   b text NOT NULL
                                );
        """.trimIndent()
    )
}

fun Random.nextString(length: Int = 10): String {
    val charPool = ('a'..'z') + ('A'..'Z')
    return (1..length)
        .map { nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}

val yasbPluginDeclaration = """id("com.github.mejiomah17.yasb.gradle-plugin")"""
fun runBuild(dir: TemporaryFolder, command: String = "build") {
    GradleRunner.create()
        .withProjectDir(dir.root)
        .withArguments(command, "--stacktrace")
        .build()
}
