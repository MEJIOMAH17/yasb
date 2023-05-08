plugins {
    id("com.android.library")
    kotlin("multiplatform")
}
kotlin {
    android() {
        publishAllLibraryVariants()
    }
    publishing {
        publications.all {
            val target = this@all
            if (target.name == "kotlinMultiplatform") {
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == target }
                    .all {
                        this.publication.artifactId = project.name + "-mpp"

                    }
            }

        }
    }
    sourceSets {
        val androidMain by getting {
            dependencies {
                api(project(":core"))
                api(project(":database:sqlite"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(project(":database:sqlite:test-fixtures"))
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
        minSdk = 26
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
afterEvaluate {
    publishing {
        publications.withType<MavenPublication>().all {
            groupId = rootProject.group.toString()
            println(this.artifactId)
            println(artifactId.replace("and.*id".toRegex(), project.name()))
            artifactId = artifactId.replace("and.*id".toRegex(), project.name())
            version = rootProject.version.toString()
        }
    }
}