kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":database-postgres-jdbc"))
                api(project(":gradle-plugin-generator"))

                implementation(kotlin("stdlib"))
                implementation(libs.postgresql)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.hikaricp)
                implementation(libs.testcontainers.postgresql)
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
