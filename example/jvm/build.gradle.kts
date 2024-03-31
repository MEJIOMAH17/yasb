import com.github.mejiomah17.yasb.Database
import com.github.mejiomah17.yasb.GenerateTablesTask
import org.testcontainers.utility.DockerImageName

plugins {
    kotlin("jvm")
    id("io.github.mejiomah17.yasb")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation(libs.yasb.postgres.jvm)
    implementation(libs.postgresql)
    implementation(libs.testcontainers.postgresql)
    implementation(libs.ktor.server)
    implementation(libs.serialization)
    implementation(libs.yakl)
    implementation(libs.flyway.core)
    implementation(libs.hikaricp)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.awaitility)
    testImplementation(libs.ktor.client)
    testImplementation(libs.ktor.client.core)
}

tasks.withType<GenerateTablesTask> {
    database = Database.Postgres(DockerImageName.parse("postgres").withTag("16.1"))
    packageName = "com.github.mejiomah17.yasb"
    flywayMigrationDirs.add(projectDir.resolve("src/main/resources/db/migration"))
}
kotlin {
    compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")
}
project.tasks.getByName("runKtlintCheckOverMainSourceSet").mustRunAfter(
    tasks.getByName("generateTables")
)
tasks.withType<Test> {
    useJUnitPlatform()
}
