import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds") version "1.0.4-SNAPSHOT"
}

winds {
  moduleMetadata {
    bomOptions {
      excludedScopes.addAll("test")
    }

    artifactDescriptor {
      name.set("BoM")
      version.set(createVersion(1, 0, 0) {
        alphaRelease(5)
      })
      nameFormat.set(NameFormat.FULL)
      artifactIdFormat.set(ArtifactIdFormat.NAME_ONLY)
    }
  }
}
