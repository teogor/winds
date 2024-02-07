pluginManagement {
  includeBuild("../")
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
      from(files("${rootDir.parentFile}/gradle/libs.versions.toml"))
    }
  }
}

include(":bom")

include(":demo")
include(":demo-1")
include(":demo-2")

include(":kmp:android")

include(":module:library-1")
include(":module:library-2")
include(":module:library-3")
include(":module:library-4")
