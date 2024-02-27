[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha01`](../releases.md)

### BoM Releases

The BoM (Bill of Materials) is the central repository for managing library versions within the
Winds Examples project. It streamlines the process of tracking the latest versions of key components and
dependencies, ensuring that your project remains up-to-date and compatible with the latest
advancements.

Here's a summary of the latest BoM versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 24 Feb 2024 |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Winds Examples libraries, including Build of
Materials (BoM) and individual libraries, in TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    winds-examples = "1.0.0-alpha01"
    winds-examples-kmp = "1.0.0-alpha01"
    winds-examples-module-1 = "0.0.0-beta05"
    winds-examples-module-1 = "1.0.0-alpha01"
    winds-examples-module-1 = "0.0.0"
    winds-examples-module-1 = "6.2.4"

    [libraries]
    winds-examples-demo-tst-2 = { group = "dev.teogor.winds.examples", name = "winds-examples-demo-kotlin-dsl-tst2", version.ref = "winds-examples" }
    winds-examples-kmp-android = { group = "dev.teogor.winds.examples", name = "kmp-android", version.ref = "winds-examples-kmp" }
    winds-examples-module-1-library-1 = { group = "dev.teogor.winds.examples", name = "module-1-library-1", version.ref = "winds-examples-module-1" }
    winds-examples-module-1-library-2 = { group = "dev.teogor.winds.examples", name = "module-1-library-2", version.ref = "winds-examples-module-1" }
    winds-examples-module-1-library-3 = { group = "dev.teogor.winds.examples", name = "module-1-library-3", version.ref = "winds-examples-module-1" }
    winds-examples-module-1-library-4 = { group = "dev.teogor.winds.examples", name = "module-1-library-4", version.ref = "winds-examples-module-1" }
    ```

=== "Using BoM"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    winds examples-bom = "1.0.0-alpha01"

    [libraries]
    winds-examples-bom = { group = "dev.teogor.winds.examples", name = "bom", version.ref = "winds examples-bom" }
    winds-examples-demo-tst-2 = { group = "dev.teogor.winds.examples", name = "winds-examples-demo-kotlin-dsl-tst2" }
    winds-examples-kmp-android = { group = "dev.teogor.winds.examples", name = "kmp-android" }
    winds-examples-module-1-library-1 = { group = "dev.teogor.winds.examples", name = "module-1-library-1" }
    winds-examples-module-1-library-2 = { group = "dev.teogor.winds.examples", name = "module-1-library-2" }
    winds-examples-module-1-library-3 = { group = "dev.teogor.winds.examples", name = "module-1-library-3" }
    winds-examples-module-1-library-4 = { group = "dev.teogor.winds.examples", name = "module-1-library-4" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
      // When Using Winds Examples BoM
      implementation(platform(libs.winds.examples.bom))

      // Winds Examples Libraries
      implementation(libs.winds.examples.demo.tst.2)
      implementation(libs.winds.examples.kmp.android)
      implementation(libs.winds.examples.module.1.library.1)
      implementation(libs.winds.examples.module.1.library.2)
      implementation(libs.winds.examples.module.1.library.3)
      implementation(libs.winds.examples.module.1.library.4)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
      // When Using Winds Examples BoM
      implementation platform(libs.winds.examples.bom)

      // Winds Examples Libraries
      implementation libs.winds.examples.demo.tst.2
      implementation libs.winds.examples.kmp.android
      implementation libs.winds.examples.module.1.library.1
      implementation libs.winds.examples.module.1.library.2
      implementation libs.winds.examples.module.1.library.3
      implementation libs.winds.examples.module.1.library.4
    }
    ```
