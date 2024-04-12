pluginManagement {
  includeBuild("../../") {
    name = "winds-build-ceres"
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

include(":no-winds")
include(":bom")
include(":core:common")
include(":core:designsystem")
include(":core:domain")
include(":core:ksp")
include(":core:ktx")
include(":core:network")
include(":data:database")
include(":data:datastore")
include(":ui:design-system")
include(":ui:ui")
