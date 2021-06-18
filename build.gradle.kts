plugins {
    kotlin("jvm") version "1.5.10"
    `maven-publish`
    `java-library`
    signing
}

group = "dev.jhseo"
version = "1.0"

repositories {
    mavenCentral()
}
