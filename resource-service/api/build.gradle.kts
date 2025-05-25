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

    implementation(libs.spring.boot.starter.actuator)
    implementation((libs.spring.boot.starter.validation))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}