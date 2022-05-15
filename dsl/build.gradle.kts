dependencies {
    implementation(kotlin("stdlib"))
    api(project(":core"))
    testImplementation ("io.kotest:kotest-assertions-core:5.2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
