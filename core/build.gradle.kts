kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.github.mejiomah17:concurrent-collections:1.0-SNAPSHOT")
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
