## Libraries Implementation Version Catalog

This catalog provides the implementation details of Winds libraries, including Build of Materials (BoM) and individual libraries, in TOML format.

```toml
[versions]
winds-bom = "1.0.0-alpha01"

[libraries]
# Winds BoM
winds-bom = { group = "dev.teogor.winds", name = "bom", version.ref = "winds-bom" }
# Winds Libraries
winds-library-1 = { group = "dev.teogor.winds", name = "module-1-library-1" }
winds-library-2 = { group = "dev.teogor.winds", name = "module-1-library-2" }
winds-library-3 = { group = "dev.teogor.winds", name = "module-1-library-3" }
winds-library-4 = { group = "dev.teogor.winds", name = "module-1-library-4" }
```

## Libraries Implementation build.gradle.kts File

This section presents the implementation dependencies for Winds libraries in a Kotlin build.gradle.kts file format.

```kotlin
dependencies {
  // Winds BoM
  implementation(platform(libs.winds.bom))
  // Winds Libraries
  implementation(libs.winds.library.1)
  implementation(libs.winds.library.2)
  implementation(libs.winds.library.3)
  implementation(libs.winds.library.4)
}
```

