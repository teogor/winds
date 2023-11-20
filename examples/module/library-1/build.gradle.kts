import dev.teogor.winds.gradle.utils.copyVersion

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  mavenPublish {
    displayName = "Library 1"
    name = "library-1"
    description = "M#1 Library 1 Description set in here"

    version = copyVersion {
      betaRelease(5)
    }
  }
}

dependencies {
  api(project(":module:library-2"))
  implementation(project(":module:library-3"))
  compileOnly(project(":module:library-4"))
}
