plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "rs.neozoic.reservation"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))

    implementation((libs.spring.boot.starter.data.jpa))

    testImplementation(kotlin("test"))
}

tasks.bootJar { enabled = false }

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}