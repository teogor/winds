import dev.teogor.winds.api.model.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "KMP"
    name = "kmp"

    artifactIdElements = 2

    canBePublished = false

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}
