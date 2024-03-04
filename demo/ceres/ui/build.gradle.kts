import dev.teogor.winds.ktx.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    description = """
    |The module encapsulates the design principles and components necessary for constructing user interfaces within our application ecosystem.
    """.trimMargin()

    artifactDescriptor {
      name = "UI"
      version = createVersion(1, 0, 0) {
        alphaRelease(2)
      }
    }
  }

  publishingOptions {
    publish = false
  }
}
