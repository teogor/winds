import dev.teogor.winds.api.ArtifactIdFormat

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

windsLegacy {
  mavenPublish {
    displayName = "Library 2"
    name = "library-2"
  }
}

winds {
  moduleMetadata {
    name = "Library 2"

    artifactDescriptor {
      name = "library-2"
      artifactIdFormat = ArtifactIdFormat.FULL
    }
  }
}
