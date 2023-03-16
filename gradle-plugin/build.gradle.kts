import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
}
dependencies {
    implementation(project(":dsl:generator:flyway"))
    implementation(project(":dsl:generator:postgres"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    testImplementation("io.kotest:kotest-assertions-core:5.2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.3")
}
tasks.withType<Test>() {
    useJUnitPlatform()
    dependsOn(project(":dsl:postgres").tasks.getByName("publishToMavenLocal"))
}
val pluginName = "${rootProject.group}.${name()}"
gradlePlugin {
    plugins {
        create(pluginName) {
            id = pluginName
            implementationClass = "com.github.mejiomah17.yasb.GeneratorPlugin"
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
            object Version{
             val yasbVersion = "${rootProject.version}"
             val kotlinVersion = "${kotlin.coreLibrariesVersion}"
            }
            """.trimIndent()
        )
    }
}
tasks.withType<KotlinCompile>() {
    dependsOn(generateVersion)
}
kotlin {
    this.sourceSets.main.get().kotlin.srcDir(generated)
}