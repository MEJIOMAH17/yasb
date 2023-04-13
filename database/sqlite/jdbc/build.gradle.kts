kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core:jdbc"))
                api(project(":database:sqlite"))
                api(project(":dsl"))
                implementation("org.xerial:sqlite-jdbc:3.41.2.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("com.zaxxer:HikariCP:5.0.0")
                implementation("io.kotest:kotest-assertions-core:5.0.2")
                implementation("org.junit.jupiter:junit-jupiter:5.8.2")
                implementation("io.mockk:mockk:1.12.3")
                implementation(project(":dsl:test-fixtures"))
            }
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}