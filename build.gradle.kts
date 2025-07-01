import org.gradle.kotlin.dsl.add
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0"
    id("com.vanniktech.maven.publish") version "0.33.0"
//    id("org.jlleitschuh.gradle.ktlint") version "12.2.0" TODO uncomment after 2.2.0 support
    java
}
buildscript {
    repositories {
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
    }
}

repositories {
    mavenCentral()
}
val jvmProjects = setOf(project(":gradle-plugin"))
val aarProjects = setOf(project(":database-sqlite-aar"))
val mppProjects = subprojects - jvmProjects - aarProjects
val androidOnlyProjects = setOf(project(":database-sqlite-android"))
val mppProjectsWithJvmTarget = mppProjects - androidOnlyProjects
val mppProjectsWithAndroidTarget = androidOnlyProjects
val projectsWithPublication =
    subprojects - setOf(project(":core-test-fixtures"), project("core-jdbc-test-fixtures"))

subprojects {
//    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>() TODO uncomment after 2.2.0 support
    configureRepositories()
    if (!name().contains("generator")) {

        tasks.withType<KotlinCompile>().all {
            compilerOptions.jvmTarget.assign(JvmTarget.JVM_1_8)
            compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")
            explicitApiMode.set(ExplicitApiMode.Strict)
        }
    }

    if (project in mppProjects) {
        project.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        project.configure<KotlinMultiplatformExtension> {
            val mpp = this
            afterEvaluate {
                if (project in mppProjectsWithJvmTarget) {
                    mpp.jvm {
                        val main by compilations.getting {
                            compilerOptions.configure {
                                jvmTarget.set(JvmTarget.JVM_1_8)
                            }
                        }
                    }
                }
                mpp.compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")
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
    apply<com.vanniktech.maven.publish.MavenPublishPlugin>()
    afterEvaluate {
        val nexusUsername: String? = project.properties.getOrDefault("nexusUsername", null)?.toString()
        mavenPublishing {
            publishToMavenCentral()
            signAllPublications()

            pom {
                name = "An YASB ${project.name} module"
                description = name.get()
                url = "https://github.com/MEJIOMAH17/yaksb"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://opensource.org/license/mit/"
                    }
                }
                developers {
                    developer {
                        id = nexusUsername
                        name = "Mark Epshtein"
                        email = "epshteinme@gmail.com"
                    }
                }
                scm {
                    url = "scm:git:git://github.com/MEJIOMAH17/yaksb.git"
                    connection = "scm:git:ssh://git@github.com/MEJIOMAH17/yaksb.git"
                    developerConnection = "https://github.com/MEJIOMAH17/yaksb"
                }
            }
        }
        configure<SigningExtension>() {
            val signingKeyLocation: String by project
            val secretKey = File(signingKeyLocation).readText()
            val signingPassword: String by project
            useInMemoryPgpKeys(secretKey, signingPassword)
            publishing.publications.configureEach {
                sign(this)
            }
        }
    }
}

// TODO uncomment after 2.2.0 support
//tasks.build.configure {
//    dependsOn(tasks.ktlintFormat)
//}
