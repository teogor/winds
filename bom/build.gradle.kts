import dev.teogor.winds.api.model.Version

plugins {
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "BoM"
    name = "bom"
    description = "BoM packing for winds"

    artifactIdElements = 1

    version = Version(
      major = 1,
      minor = 0,
      patch = 0,
    ).setAlphaRelease(1)

    setBomOptions {
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
