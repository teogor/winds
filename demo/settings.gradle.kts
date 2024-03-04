pluginManagement {
  includeBuild("../") {
    name = "winds-build-demo"
  }

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

rootProject.name = "winds-demo"

enum class Project(val path: String) {
  CERES("ceres"),
  QUERENT("querent"),
  SUDOKLIFY("sudoklify")
}

fun includeProject(project: Project) {
  when (project) {
    Project.CERES -> includeBuild(Project.CERES.path)
    Project.QUERENT -> includeBuild(Project.QUERENT.path)
    Project.SUDOKLIFY -> includeBuild(Project.SUDOKLIFY.path)
  }
}

includeProject(Project.CERES)
