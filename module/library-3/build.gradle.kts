import dev.teogor.winds.api.model.Version

plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  mavenPublish {
    displayName = "Library 3"
    name = "library-3"

    version = Version(
      major = 3,
      minor = 8,
      patch = 2,
    ).markAsDeprecated()
  }
}
