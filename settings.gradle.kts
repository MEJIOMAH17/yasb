rootProject.name = "yasb"
include(
    "core",
    "core:jdbc",
    "core:jdbc:postgres",
    "dsl",
    "dsl:test-fixtures",
    "dsl:postgres",
    "dsl:sqlite",
    "dsl:generator",
    "dsl:generator:flyway",
    "dsl:generator:postgres",
    "dsl:generator:sqlite",
    "database:sqlite",
    "database:sqlite:jdbc",
    "database:sqlite:android",
    "gradle-plugin"
)
pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}
