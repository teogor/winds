import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "Data"
      version = createVersion(1, 0, 0) {
        alphaRelease(4)
      }
    }
  }

  publishing {
    enabled = false
  }
}
