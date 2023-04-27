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
                implementation(project(":dsl:test-fixtures"))
                implementation("io.kotest:kotest-assertions-core:5.2.2")
                implementation("org.junit.jupiter:junit-jupiter:5.8.2")
                implementation("io.mockk:mockk:1.12.3")
            }
        }
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>() {
    this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}
