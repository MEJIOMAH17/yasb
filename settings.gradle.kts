rootProject.name = "yasb"
include(
    "core",
    "core:jdbc",
    "dsl",
    "dsl:test-fixtures",
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
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}
