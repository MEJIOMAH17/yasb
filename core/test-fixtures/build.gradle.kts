kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation("io.kotest:kotest-assertions-core:5.2.2")
                implementation("org.junit.jupiter:junit-jupiter:5.8.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
