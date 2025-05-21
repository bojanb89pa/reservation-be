plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "reservation"

include("auth-service")

//dependencyResolutionManagement {
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/libs.versions.toml"))
//        }
//    }
//}
include("resource-service")
include("auth-service:data")
include("domain")
include("auth-service:api")
include("auth-service:application")
include("resource-service:data")
findProject(":resource-service:data")?.name = "data"
include("resource-service:api")
findProject(":resource-service:api")?.name = "api"
include("resource-service:application")
findProject(":resource-service:application")?.name = "application"
