plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "rs.neozoic.reservation"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.actuator)

    implementation((libs.spring.boot.starter.oauth2.authorization.server))
    implementation((libs.spring.boot.starter.security))
    implementation((libs.spring.boot.starter.validation))
    implementation(libs.spring.boot.starter.web)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Test
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)

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