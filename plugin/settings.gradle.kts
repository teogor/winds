dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  versionCatalogs {
    create("libs") {
      from(files("${rootDir.parentFile}/gradle/libs.versions.toml"))
    }
  }
}

include(":winds")
include(":winds-api")
