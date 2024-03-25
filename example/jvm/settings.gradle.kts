pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("io.github.mejiomah17.yasb").version(extra["version"] as String)
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        val ktorVersion = "2.3.7"
        create("libs") {
            library("yasb.postgres.jvm","io.github.mejiomah17.yasb:database-postgres-jdbc-jvm:${extra["version"]}")
            library("ktor-server","io.ktor:ktor-server-netty:$ktorVersion")
            library("ktor-client","io.ktor:ktor-client-cio:$ktorVersion")
            library("ktor-client-core","io.ktor:ktor-client-core:$ktorVersion")
            library("serialization","org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            library("yakl","io.github.mejiomah17.yakl:stdout:0.1.7")
            library("testcontainers-postgresql", "org.testcontainers:postgresql:1.19.3")
            library("flyway-core", "org.flywaydb:flyway-core:9.22.3")
            library("postgresql", "org.postgresql:postgresql:42.7.1")
            library("hikaricp", "com.zaxxer:HikariCP:5.1.0")

            library("kotest-assertions-core", "io.kotest:kotest-assertions-core:5.8.0")
            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:5.10.1")
            library("mockk", "io.mockk:mockk:1.13.9")
            library("awaitility", "org.awaitility:awaitility:4.2.0")


        }
    }
}
