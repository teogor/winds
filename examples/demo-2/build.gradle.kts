import dev.teogor.winds.ktx.createVersion

plugins {
  `kotlin-dsl`
  id("dev.teogor.winds")
}

windsLegacy {
  mavenPublish {
    displayName = "Demo Tst 2"
    name = "demo-kotlin-dsl-tst2"
    description = "Demo 2 Description set in here"

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }
  }
}

winds {
  moduleMetadata {
    name = "Demo 2"

    artifactDescriptor {
      name = "demo 2"
    }
  }
}
