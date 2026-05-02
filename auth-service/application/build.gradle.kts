plugins {
    kotlin("jvm")
}

group = "rs.neozoic.reservation"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.mockito.kotlin)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}