import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "Core"
      version = createVersion(1, 0, 0) {
        betaRelease(2)
      }
    }
  }

  publishing {
    enabled = false
  }
}
