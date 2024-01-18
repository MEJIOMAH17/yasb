kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
