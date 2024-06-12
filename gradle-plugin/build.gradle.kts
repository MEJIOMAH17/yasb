import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    java
}
java {
    withJavadocJar()
    withSourcesJar()
}
dependencies {
    implementation(project(":gradle-plugin-generator-flyway"))
    implementation(project(":database-postgres-jdbc-generator"))
    implementation(project(":database-sqlite-generator"))
    implementation(libs.kotlin.gradle.plugin)

    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
}
tasks.withType<Test>() {
    useJUnitPlatform()
    rootProject.subprojects.forEach {
        it.tasks.findByName("publishToMavenLocal")?.also { publish ->
            dependsOn(publish)
        }
    }
}
version = rootProject.version
gradlePlugin {
    plugins {
        create(name) {
            id = "$group"
            implementationClass = "com.github.mejiomah17.yasb.GeneratorPlugin"
            displayName = "An YASB gradle plugin"
            description = "An YASB gradle plugin for codegen"
        }
    }
}
val generated = project.buildDir.resolve("generated/kotlin").also {
    it.mkdirs()
}
val generateVersion = tasks.register("generateVersion") {
    doLast {
        generated.resolve("Version.kt").writeText(
            """
            object Version {
                val yasbVersion = "${rootProject.version}"
                val kotlinVersion = "${kotlin.coreLibrariesVersion}"
            }
            
            """.trimIndent()
        )
    }
}

val generateLocalProperties = tasks.register("generateLocalProperties") {
    doLast {
        generated.resolve("LocalProperties.kt").writeText(
            """
            val localProperties = ""${'"'} ${rootProject.rootDir.resolve("local.properties").readText()}""${'"'}
            """.trimIndent().trim()
        )
    }
}
tasks.withType<KotlinCompile>() {
    dependsOn(generateVersion)
    dependsOn(generateLocalProperties)
}
kotlin {
    this.sourceSets.main.get().kotlin.srcDir(generated)
}
java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))
tasks.withType<KotlinCompile>().all {
    this.kotlinOptions.jvmTarget = "11"
}
ktlint.filter {
    exclude {
        it.file.absolutePath.contains("generated")
    }
}
afterEvaluate {
    publishing {
        publications {
            this.withType(MavenPublication::class.java) {
                this.artifactId = project.group.toString() + ".gradle.plugin"
            }
        }
    }
}
