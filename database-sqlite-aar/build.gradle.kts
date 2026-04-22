plugins {
    base
    id("maven-publish")
}
val artifact = artifacts.add("default", file("sqlite-android-3420000.aar"))

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(artifact)
        }
    }
}
