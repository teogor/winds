import dev.teogor.winds.api.model.Version

plugins {
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "Module 1"
    name = "module-1"
    description = "Module 1 Description set in here"

    canBePublished = false

    version = Version(
      major = 1,
      minor = 0,
      patch = 0,
    ).setAlphaRelease(1)
  }
}
