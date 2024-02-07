import dev.teogor.winds.api.model.createVersion

plugins {
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "BoM"
    name = "bom"
    description = "BoM packing for winds"

    artifactIdElements = 1

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }

    defineBoM()

    defineBoM {
      acceptedModules = acceptedModules.plus(listOf(
        ":demo",
        ":demo-1",
        ":demo-2",
      ))
      acceptedPaths = acceptedPaths.plus(listOf(
        ":module",
      ))
    }
  }
}
