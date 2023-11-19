import dev.teogor.winds.api.model.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "Module 1"
    name = "module-1"
    description = "Module 1 Description set in here"

    canBePublished = false

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}
