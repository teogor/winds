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

include("api")
include("common")
include("gradle-plugin")

// includeBuild("examples")
