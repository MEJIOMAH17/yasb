kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core-jdbc"))
                api(project(":database-sqlite"))
                implementation(libs.sqlite.jdbc)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core-jdbc-test-fixtures"))
                implementation(project(":core-test-fixtures"))
                implementation(project(":database-sqlite-test-fixtures"))
                implementation(libs.awaitility)
                implementation(libs.hikaricp)
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
