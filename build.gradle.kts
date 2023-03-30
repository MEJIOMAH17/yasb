
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    java
    id("maven-publish")
}
group = "com.github.mejiomah17.yasb"
version = "0.5.0-kotlin-${project.property("kotlin.version")}"

repositories {
    mavenCentral()
}
val jvmProjects = setOf(project(":gradle-plugin"))
val mppProjects = subprojects - jvmProjects
val projectsWithPublication = subprojects - project(":dsl:test-fixtures")

subprojects {
    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    configureRepositories()
    if (!name().contains("generator")) {
        tasks.withType<KotlinCompile>().all {
            this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
        }
    }
    if (project in jvmProjects) {
        apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()
    } else if (project in mppProjects) {
        project.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
            afterEvaluate {
                tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>() {
                    this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
                }
            }
        }
    }
    if (project in projectsWithPublication) {
        configurePublication()
    }
}
fun Project.configureRepositories() {
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
}

fun Project.configurePublication() {
    apply<MavenPublishPlugin>()
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
        if (project in jvmProjects) {
            java {
                withSourcesJar()
            }
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])
                    this.groupId = rootProject.group.toString()
                    this.artifactId = project.name()
                    this.version = rootProject.version.toString()
                }
            }
        } else if (project in mppProjects) {
            project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
                publications {
                    val publicationsFromMainHost =
                        listOf(jvm()).map { it.name } + "kotlinMultiplatform"
                    matching { it.name in publicationsFromMainHost }.all {
                        val targetPublication = this@all
                        tasks.withType<AbstractPublishToMaven>()
                            .matching { it.publication == targetPublication }
                            .configureEach {
//                                onlyIf { findProperty("isMainHost") == "true" }
                                publication.run {
                                    groupId = rootProject.group.toString()
                                    artifactId = artifactId.replace(project.name, project.name())
                                    version = rootProject.version.toString()
                                }
                            }
                    }
                }
            }
        }
    }
}

tasks.build.configure {
    dependsOn(tasks.ktlintFormat)
}
