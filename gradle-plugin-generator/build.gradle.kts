kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                implementation(kotlin("stdlib"))
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
