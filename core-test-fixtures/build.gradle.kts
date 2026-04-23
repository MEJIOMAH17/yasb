kotlin {
    jvm {
    }
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(kotlin("test"))
                implementation(libs.kotest.assertions.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}
