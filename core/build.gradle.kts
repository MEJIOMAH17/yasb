kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.github.mejiomah17:concurrent-collections:1.0-SNAPSHOT")
            }
        }
    }
}
