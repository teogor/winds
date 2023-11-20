import dev.teogor.winds.api.model.Version

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  mavenPublish {
    displayName = "Library 4"
    name = "library-4"

    version = Version(
      major = 6,
      minor = 2,
      patch = 4,
    )
  }
}
