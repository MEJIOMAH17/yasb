kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":database:postgres:jdbc"))
                api(project(":gradle-plugin:generator"))

                implementation(kotlin("stdlib"))
                implementation("org.postgresql:postgresql:42.3.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("com.zaxxer:HikariCP:5.0.0")
                implementation("org.testcontainers:postgresql:1.17.1")
                implementation("io.kotest:kotest-assertions-core:5.0.2")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
