import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

windsLegacy {
  mavenPublish {
    displayName = "Module 1"
    name = "module-1"
    description = "Module 1 Description set in here"

    canBePublished = false

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}

winds {
  moduleMetadata {
    name = "Module"

    artifactDescriptor {
      name = "module"
      version = createVersion(1, 0, 0) {
        alphaRelease(1)
      }
    }
  }

  publishingOptions {
    publish = false
  }
}
