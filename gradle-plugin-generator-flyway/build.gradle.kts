kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":gradle-plugin-generator"))
                api(libs.postgresql)
                api(libs.flyway.core)
                api(libs.hikaricp)
                api(libs.testcontainers.postgresql)

                implementation(kotlin("stdlib"))
            }
        }
    }
}
