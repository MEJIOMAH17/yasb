import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    java
    id("maven-publish")
}

group = "com.github.mejiomah17.yasb"
version = "0.1.0-kotlin-${kotlin.coreLibrariesVersion}"

repositories {
    mavenCentral()
}
allprojects {
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
    apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()
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

fun Project.name(): String {
    val projects = arrayListOf<Project>()
    var project = this
    while (project != rootProject) {
        projects.add(project)
        project = project.parent!!
    }
    return projects.asReversed().map { it.name }.joinToString("-")
}
