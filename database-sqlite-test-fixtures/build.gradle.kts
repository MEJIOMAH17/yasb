kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core-test-fixtures"))
                api(project(":database-sqlite"))
                implementation("io.kotest:kotest-assertions-core:5.2.2")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
