pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "Winds"

include("api")
include("common")
include("gradle-plugin")

includeBuild("examples")
