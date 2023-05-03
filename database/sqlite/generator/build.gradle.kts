kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":database:sqlite"))
                api(project(":gradle-plugin:generator"))
                implementation("org.xerial:sqlite-jdbc:3.41.2.1")
                implementation(kotlin("stdlib"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":database:sqlite:jdbc"))
                implementation("com.zaxxer:HikariCP:5.0.0")
                implementation("io.kotest:kotest-assertions-core:5.0.2")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}
