pluginManagement {
  includeBuild("../../") {
    name = "winds-build-sudoklify"
  }

  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }

  versionCatalogs {
    create("libs") {
      from(files("../../gradle/libs.versions.toml"))
    }
  }
}

include(":sudoklify-common")
include(":sudoklify-core")
include(":sudoklify-ktx")
include(":sudoklify-seeds")
