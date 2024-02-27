import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    isBom = true
    bomOptions {
      excludedScopes += listOf("test")
    }

    artifactDescriptor {
      name = "BoM"
      version = createVersion(1, 0, 0) {
        alphaRelease(1)
      }
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.NAME_ONLY
    }
  }
}

windsLegacy {
  mavenPublish {
    displayName = "BoM"
    name = "bom"
    description = "BoM packing for winds"

    artifactIdElements = 1

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}
