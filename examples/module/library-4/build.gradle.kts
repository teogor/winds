import dev.teogor.winds.api.model.Version

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

windsLegacy {
  mavenPublish {
    displayName = "Library 4"
    name = "library-4"

    version = Version(
      major = 6,
      minor = 2,
      patch = 4,
    )
  }
}

winds {
  moduleMetadata {
    name = "Library 4"

    artifactDescriptor {
      name = "library-4"
    }
  }
}
