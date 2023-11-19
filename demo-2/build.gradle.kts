import dev.teogor.winds.api.model.createVersion

plugins {
  `kotlin-dsl`
  id("dev.teogor.winds")
}

winds {
  mavenPublish {
    displayName = "Demo Tst 2"
    name = "demo-kotlin-dsl-tst2"
    description = "Demo 2 Description set in here"

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}
