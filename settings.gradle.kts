rootProject.name = "yasb"
include(
    "core",
    "core-test-fixtures",
    "core-jdbc",
    "core-jdbc-test-fixtures",
    "database-sqlite",
    "database-sqlite-aar",
    "database-sqlite-android",
    "database-sqlite-generator",
    "database-sqlite-jdbc",
    "database-sqlite-test-fixtures",
    "database-postgres",
    "database-postgres-jdbc",
    "database-postgres-jdbc-generator",
    "gradle-plugin",
    "gradle-plugin-generator",
    "gradle-plugin-generator-flyway"
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
