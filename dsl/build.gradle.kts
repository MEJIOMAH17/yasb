kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api(project(":core:jdbc"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.2.2")
                implementation("org.junit.jupiter:junit-jupiter:5.8.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
