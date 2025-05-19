plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "rs.neozoic.reservation"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":auth-service:data"))
    implementation(project(":auth-service:application"))
    implementation(project(":auth-service:api"))

    implementation((libs.spring.boot.starter.oauth2.authorization.server))
    implementation((libs.spring.boot.starter.security))
    implementation((libs.spring.boot.starter.validation))
    implementation(libs.spring.boot.starter.web)
    implementation((libs.spring.boot.starter.data.jpa))
    implementation((libs.spring.boot.starter.data.rest))

    implementation(libs.jackson.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.liquibase.core)

    compileOnly(libs.lombok)
    developmentOnly(libs.spring.boot.devtools)
//    developmentOnly(libs.spring.boot.docker.compose)
    runtimeOnly(libs.h2)
    runtimeOnly(libs.postgresql)


    annotationProcessor(libs.spring.boot.configuration.processor)
    annotationProcessor(libs.lombok)

    // Test
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)

}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}