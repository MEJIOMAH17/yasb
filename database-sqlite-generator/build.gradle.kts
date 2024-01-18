kotlin {
    jvm() {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":database-sqlite"))
                api(project(":gradle-plugin-generator"))
                implementation(libs.sqlite.jdbc)
                implementation(kotlin("stdlib"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":database-sqlite-jdbc"))
                implementation(libs.hikaricp)
                implementation(libs.kotest.assertions.core)
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}
