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

include("winds-api")
include("winds-common")
include("winds-gradle-plugin")

includeBuild("examples/ceres")
includeBuild("examples/querent")
includeBuild("examples/sudoklify")
includeBuild("examples/multiplatform")
