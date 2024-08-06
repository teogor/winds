pluginManagement {
  includeBuild("../../") {
    name = "winds-build-multiplatform"
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

include(":common")
