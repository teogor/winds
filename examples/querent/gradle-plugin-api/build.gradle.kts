plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "Gradle Plugin API"
    }

    documentationBuilder {
      isOptional = true
    }
  }
}
