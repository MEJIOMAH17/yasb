dependencies {
    implementation(kotlin("stdlib"))
    api(project(":dsl"))
    api(project(":core:jdbc:postgres"))
    implementation("org.postgresql:postgresql:42.3.3")
    testImplementation("com.zaxxer:HikariCP:5.0.0")
    testImplementation("org.testcontainers:postgresql:1.17.1")
    testImplementation("io.kotest:kotest-assertions-core:5.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation(project(":dsl").dependencyProject.sourceSets.test.get().output)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
