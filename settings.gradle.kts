rootProject.name = "yasb"
include(
    "core"
//    "core:jdbc",
//    "core:jdbc:postgres",
//    "dsl",
//    "dsl:postgres",
//    "dsl:generator",
//    "dsl:generator:flyway",
//    "dsl:generator:postgres",
//    "gradle-plugin"
)
pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}
