rootProject.name = "yaksb"
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
    "gradle-plugin-generator-flyway",
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
        id("com.android.library").version("8.5.2")
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("awaitility", "org.awaitility:awaitility:4.2.1")
            library("stately-collections", "co.touchlab:stately-concurrent-collections:2.1.0")
            library("kotest-assertions-core", "io.kotest:kotest-assertions-core:5.9.1")
            library("junit", "junit:junit:4.13.2")
            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:5.10.2")
            library("mockk", "io.mockk:mockk:1.13.11")
            library("postgresql", "org.postgresql:postgresql:42.7.10")
            library("hikaricp", "com.zaxxer:HikariCP:7.0.2")
            library("testcontainers-postgresql", "org.testcontainers:postgresql:1.21.4")
            library("androidx-test-runner", "androidx.test:runner:1.5.2")
            library("androidx-test-rules", "androidx.test:rules:1.5.0")
            library("androidx-sqlite", "androidx.sqlite:sqlite:2.4.0")
            library("sqlite-jdbc", "org.xerial:sqlite-jdbc:3.46.0.0")
            library("kotlin-gradle-plugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlin.version"] as String}")
            library("flyway-core", "org.flywaydb:flyway-core:9.22.3")
        }
    }
}
