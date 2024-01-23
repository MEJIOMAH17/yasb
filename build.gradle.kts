import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("multiplatform") apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    java
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.9.10"
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
    apply(plugin = "org.jetbrains.dokka")
    configureRepositories()
    if (!name().contains("generator")) {
        tasks.withType<KotlinCompile>().all {
            this.kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
            this.kotlinOptions.jvmTarget = "1.8"
            this.explicitApiMode.set(ExplicitApiMode.Strict)
        }
    }

    if (project in mppProjects) {
        project.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        project.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
            val mpp = this
            if (project in mppProjectsWithJvmTarget) {
                val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
                    val dokkaHtml = tasks.getByName<AbstractDokkaTask>("dokkaHtml")
                    dependsOn(dokkaHtml)
                    archiveAppendix.set("jvm")
                    archiveClassifier.set("javadoc")
                    from(dokkaHtml.outputDirectory)
                }
            }
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
    afterEvaluate {
        publishing {
            val nexusUsername: String by project
            publications {
                configureEach {
                    if (this !is MavenPublication) return@configureEach
                    if (name == "jvm") {
                        artifact(tasks.getByName("javadocJar")) {
                            classifier = "javadoc"
                        }
                    }
                    version = version.toString()
                    pom {
                        name = "An YASB ${project.name} module"
                        description = name.get()
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
                        val releasesRepoUrl =
                            URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                        val snapshotsRepoUrl =
                            URI.create("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                        name = "mavenCentral"
                        url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                        val nexusToken: String by project
                        credentials {
                            username = nexusUsername
                            password = nexusToken
                        }
                    }
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

tasks.build.configure {
    dependsOn(tasks.ktlintFormat)
}
