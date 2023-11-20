import dev.teogor.winds.gradle.utils.copyVersion

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  mavenPublish {
    displayName = "Library 3"
    name = "library-3"

    version = copyVersion {
      versionQualifier(10)
      setIsDeprecated()
    }
  }
}
