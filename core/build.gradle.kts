kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.stately.collections)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
