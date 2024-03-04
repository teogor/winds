plugins {
  alias(libs.plugins.gradle.publish)
  id("dev.teogor.winds")
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "Gradle Plugin"
    }

    documentationBuilder {
      pluginIds += listOf(
        "dev.teogor.querent",
      )
    }
  }
}
