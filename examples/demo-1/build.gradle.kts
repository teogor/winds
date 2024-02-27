plugins {
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    name = "Demo 1"

    artifactDescriptor {
      name = "demo 1"

      version = version.copy().toBuilder()
        .setIsDeprecated()
        .build()
    }
  }
}
