dependencyResolutionManagement {
  includeBuild("../../plugin/")
  @Suppress("UnstableApiUsage")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  versionCatalogs {
    create("libs") {
      from(files("${rootDir.parentFile.parentFile}/gradle/libs.versions.toml"))
    }
  }
}

include(":plugin-core")
