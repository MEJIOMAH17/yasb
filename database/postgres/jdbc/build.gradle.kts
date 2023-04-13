kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core:jdbc"))
                api(project(":database:postgres"))
                implementation("org.postgresql:postgresql:42.3.3")
            }
        }
    }
}
