plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "KSP"
    }

    documentationBuilder {
      isCompiler = true
    }
  }
}
