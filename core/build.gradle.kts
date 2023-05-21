kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("co.touchlab:stately-collections:1.2.5")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.2.2")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
