import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    java
    id("maven-publish")
}
buildscript {
    repositories {
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
    }
}
group = "com.github.mejiomah17.yasb"
version = "0.5.0-kotlin-${project.property("kotlin.version")}"

repositories {
    mavenCentral()
}
val jvmProjects = setOf(project(":gradle-plugin"))
val mppProjects = subprojects - jvmProjects
val androidOnlyProjects = setOf(project(":database:sqlite:android"))
val mppProjectsWithJvmTarget = mppProjects - androidOnlyProjects
val mppProjectsWithAndroidTarget = androidOnlyProjects
val projectsWithPublication = subprojects - setOf(project(":core:test-fixtures"), project("core:jdbc:test-fixtures"))

subprojects {
    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    configureRepositories()
    if (!name().contains("generator")) {
        tasks.withType<KotlinCompile>().all {
            this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
            this.kotlinOptions.jvmTarget = "1.8"
        }
    }
    if (project in jvmProjects) {
        apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()
    } else if (project in mppProjects) {
        project.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
            val mpp = this
            afterEvaluate {
                tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>() {
                    this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
                }
                if (project in mppProjectsWithJvmTarget) {
                    mpp.jvm() {
                        val main by compilations.getting {
                            compilerOptions.configure {
                                jvmTarget.set(JvmTarget.JVM_1_8)
                            }
                        }
                    }
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
        google()
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
        } else if (project in mppProjectsWithJvmTarget) {
            project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
                publications {
                    val publicationsFromMainHost =
                        listOfNotNull(jvm()).map { it.name } + "kotlinMultiplatform"
                    matching { it.name in publicationsFromMainHost }.all {
                        val targetPublication = this@all

                        tasks.withType<AbstractPublishToMaven>()
                            .matching { it.publication == targetPublication }
                            .configureEach {
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
