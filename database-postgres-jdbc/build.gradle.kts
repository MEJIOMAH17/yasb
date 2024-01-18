kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core-jdbc"))
                api(project(":database-postgres"))
                implementation(libs.postgresql)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core-test-fixtures"))
                implementation(project(":core-jdbc-test-fixtures"))
                implementation(libs.hikaricp)
                implementation(libs.testcontainers.postgresql)
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
