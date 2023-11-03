plugins {
  id("dev.teogor.winds")
  id("java-library")
}

winds {
  mavenPublish {
    displayName = "Library 2"
    name = "library-2"
  }
}
