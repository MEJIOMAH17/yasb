kotlin {
    jvm {
    }
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core-test-fixtures"))
                api(project(":database-sqlite"))
                implementation(libs.kotest.assertions.core)
            }
        }
    }
}
