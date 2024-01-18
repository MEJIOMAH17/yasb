kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core-test-fixtures"))
                api(project(":database-sqlite"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
