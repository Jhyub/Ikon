plugins {
    kotlin("jvm") version "1.5.10"
    maven
    signing
    `java-library`
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "dev.jhseo"
version = "0.1.0"

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("Ikon")
                description.set("Kotlin/JVM library that fetches icons on a freedesktop environment.")
                url.set("https://github.com/Jhyub/Ikon")
                licenses {
                    license {
                        name.set("MIT License")
                    }
                }
                developers {
                    developer {
                        id.set("Jhyub")
                        name.set("Janghyub Seo")
                        email.set(
                            "seojanghyeob at gmail dot com"
                                .replace(" at ", "@")
                                .replace(" dot ", ".")
                        )
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Jhyub/Ikon.git")
                    developerConnection.set("scm:git:ssh://github.com/Jhyub/Ikon.git")
                    url.set("https://github.com/Jhyub/Ikon")
                }
            }
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

signing {
    sign(publishing.publications["maven"])
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

repositories {
    mavenCentral()
}
