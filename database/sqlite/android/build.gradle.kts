plugins {
    id("com.android.library")
    kotlin("multiplatform")
}
kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                api(project(":core"))
                api(project(":database:sqlite"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("androidx.test:runner:1.5.2")
                implementation("androidx.test:rules:1.5.0")
                implementation("androidx.sqlite:sqlite:2.3.1")
                implementation("io.kotest:kotest-assertions-core:5.0.2")
            }
        }
    }
}
android {
    namespace = "com.example.namespace"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
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
