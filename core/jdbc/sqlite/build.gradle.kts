kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core:jdbc"))
                implementation("org.xerial:sqlite-jdbc:3.41.2.1")
            }
        }
    }
}
