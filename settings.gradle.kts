rootProject.name = "yasb"
include(
    "core",
    "core:jdbc",
    "dsl",
    "dsl:test-fixtures",
    "dsl:postgres",
    "dsl:generator",
    "dsl:generator:flyway",
    "dsl:generator:postgres",
    "database:sqlite",
    "database:sqlite:android",
    "database:sqlite:jdbc",
    "database:sqlite:jdbc:generator",
    "database:postgres",
    "database:postgres:jdbc",
    "gradle-plugin"
)
pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}
