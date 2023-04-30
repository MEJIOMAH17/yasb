rootProject.name = "yasb"
include(
    "core:test-fixtures",
    "core",
    "core:jdbc",
    "core:jdbc:test-fixtures",
    "dsl:generator",
    "dsl:generator:flyway",
    "database:sqlite",
    "database:sqlite:android",
    "database:sqlite:jdbc",
    "database:sqlite:jdbc:generator",
    "database:postgres",
    "database:postgres:jdbc",
    "database:postgres:jdbc:generator",
    "gradle-plugin"
)
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("com.android.library").version("7.4")
    }
}
