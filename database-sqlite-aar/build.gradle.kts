plugins {
    id("maven-publish")
}
val default = configurations.maybeCreate("default")
val artifact = artifacts.add(default.name, file("sqlite-android-3420000.aar"))

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(artifact)
        }
    }
}
