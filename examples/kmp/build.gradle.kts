import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

windsLegacy {
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

winds {
  moduleMetadata {
    name = "KMP"

    artifactDescriptor {
      name = "kmp"
      version = createVersion(1, 0, 0) {
        betaRelease(1)
      }
    }

    publishingOptions {
      cascadePublish = false
      publish = false
    }
  }
}
