kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.stately.collections)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotest.assertions.core)
                implementation(kotlin("test"))
            }
        }
    }
}
