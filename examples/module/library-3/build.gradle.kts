import dev.teogor.winds.gradle.utils.copyVersion

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

windsLegacy {
  mavenPublish {
    displayName = "Library 3"
    name = "library-3"

    version = copyVersion {
      versionQualifier(10)
      setIsDeprecated()
    }
  }
}

winds {
  moduleMetadata {
    name = "Library 3"

    artifactDescriptor {
      name = "library 3"
    }
  }
}
