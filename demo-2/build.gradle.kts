import dev.teogor.winds.api.model.Version

plugins {
  `kotlin-dsl`
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "Demo Tst 2"
    name = "demo-kotlin-dsl-tst2"
    description = "Demo 2 Description set in here"

    version = Version(
      major = 1,
      minor = 0,
      patch = 0,
    ).setAlphaRelease(1)
  }
}
