plugins {
    kotlin("jvm") version "1.5.0"
    `maven-publish`
}

group = "dev.jhseo"
version = "1.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("Ikon")
                description.set("Kotlin/JVM library that fetches icons on a freedesktop environment.")
                url.set("https://github.com/Jhyub/Ikon")

            }
        }
    }
}