rootProject.name = "yasb"
include(
    "core",
    "core:jdbc",
    "core:jdbc:postgres",
    "core:jdbc:sqlite",
    "dsl",
    "dsl:test-fixtures",
    "dsl:postgres",
    "dsl:sqlite",
    "dsl:generator",
    "dsl:generator:flyway",
    "dsl:generator:postgres",
    "dsl:generator:sqlite",
    "gradle-plugin"
)
pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}
