dependencies {
    implementation(kotlin("stdlib"))
    api(project(":dsl"))
    testImplementation("io.kotest:kotest-assertions-core:5.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
