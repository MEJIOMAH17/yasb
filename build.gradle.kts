import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    java
    id("maven-publish")
}
group = "com.github.mejiomah17.yasb"
version = "0.4.0-kotlin-${extra["kotlin.version"]}"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/MEJIOMAH17/kotlin-concurrent-collections")
            credentials {
                val githubToken: String by project
                val githubUser: String by project
                username = githubUser
                password = githubToken
            }
        }
    }

    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
//        apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()
    if (!name().contains("generator")) {
        tasks.withType<KotlinCompile>() {
            this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
        }
    }
    if (this != rootProject) {
        apply<MavenPublishPlugin>()
        apply<JavaPlugin>()
        java {
            withSourcesJar()
        }
        afterEvaluate {
            publishing {
                repositories {
                    maven {
                        url = uri("https://maven.pkg.github.com/MEJIOMAH17/yasb")
                        credentials {
                            val githubToken: String by project
                            val githubUser: String by project

                            username = githubUser
                            password = githubToken
                        }
                    }
                }
                publications {
                    create<MavenPublication>("maven") {
                        from(components["java"])
                        this.groupId = rootProject.group.toString()
                        this.artifactId = project.name()
                        this.version = rootProject.version.toString()
                    }
                }
            }
        }
    }
}

tasks.build.configure {
    dependsOn(tasks.ktlintFormat)
}
