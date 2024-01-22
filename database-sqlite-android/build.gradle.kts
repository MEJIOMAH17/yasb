plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    androidTarget() {
        publishAllLibraryVariants()
    }
    sourceSets {
        val androidMain by getting {
            dependencies {
                api(project(":core"))
                api(project(":database-sqlite"))
                api(project(path = ":database-sqlite-aar", configuration = "default"))
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(project(":database-sqlite-test-fixtures"))
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.rules)
                implementation(libs.androidx.sqlite)
                implementation(libs.kotest.assertions.core)
            }
        }
    }
}
android {
    namespace = "com.example.namespace"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/*.md")
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

tasks.getByName("check") {
    dependsOn("connectedAndroidTest")
}
