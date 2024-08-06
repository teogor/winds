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
  SUDOKLIFY("sudoklify"),
  MULTIPLATFORM("multiplatform"),
}

fun includeProject(project: Project) {
  includeBuild(project.path)
}

includeProject(Project.MULTIPLATFORM)
