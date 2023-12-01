import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("multiplatform") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    java
    id("maven-publish")
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
val projectsWithPublication = subprojects - setOf(project(":core-test-fixtures"), project("core-jdbc-test-fixtures"))

subprojects {
    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    configureRepositories()
    if (!name().contains("generator")) {
        tasks.withType<KotlinCompile>().all {
            this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
            this.kotlinOptions.jvmTarget = "1.8"
        }
    }

    if (project in mppProjects) {
        project.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
            val mpp = this
            afterEvaluate {
                tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>() {
                    this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
                }
                if (project in mppProjectsWithJvmTarget) {
                    mpp.jvm {
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
    apply<SigningPlugin>()
    publishing {
        val nexusUsername: String by project
        publications {
            configureEach {
                if (this !is MavenPublication) return@configureEach
                version = version.toString()
                pom {
                    // todo meaningful names
                    name = "YASB ${project.name} module"
                    url = "https://github.com/MEJIOMAH17/yasb"
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
                        url = "scm:git:git://github.com/MEJIOMAH17/yasb.git"
                        connection = "scm:git:ssh://git@github.com/MEJIOMAH17/yasb.git"
                        developerConnection = "https://github.com/MEJIOMAH17/yasb"
                    }
                }
            }
            repositories {
                maven {
                    val releasesRepoUrl = URI.create("https://s01.oss.sonatype.org/service/local/")
                    val snapshotsRepoUrl = URI.create("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    name = "mavenCentral"
                    url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                    logger.lifecycle("Set publication repository for version $version to $url")

                    val nexusToken: String by project
                    credentials {
                        username = nexusUsername
                        password = nexusToken
                    }
                }
            }
        }
    }
//    configure<SigningExtension>() {
//        val signingKeyId = System.getenv("ORG_GRADLE_PROJECT_signingKeyId")
//        val signingKey = System.getenv("ORG_GRADLE_PROJECT_signingKey")
//        val signingPassword = System.getenv("ORG_GRADLE_PROJECT_signingKeyPassword")
//        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
//        publishing.publications.configureEach {
//            sign(this)
//        }
//    }
}

tasks.build.configure {
    dependsOn(tasks.ktlintFormat)
}
