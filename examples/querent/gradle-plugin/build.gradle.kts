plugins {
  alias(libs.plugins.gradle.publish)
  id("dev.teogor.winds") version "1.0.4-SNAPSHOT"
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name.set("Gradle Plugin")
    }

    documentationBuilder {
      pluginIds.addAll(
        "dev.teogor.querent",
      )
    }
  }
}
