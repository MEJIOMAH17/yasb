dependencies {
    implementation(kotlin("stdlib"))
    api(project(":dsl"))
    api(project(":dsl:generator"))
    api("org.postgresql:postgresql:42.3.3")
    api("org.flywaydb:flyway-core:8.5.10")
    api("com.zaxxer:HikariCP:5.0.0")
    api("org.testcontainers:postgresql:1.17.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
