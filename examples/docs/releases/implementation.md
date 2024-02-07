[//]: # (This file was automatically generated - do not edit)

## Implementation

### Versions

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 0.0.0 | [changelog 🔗](changelog/0.0.0.md) | 07 Feb 2024 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 07 Feb 2024 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 07 Feb 2024 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 07 Feb 2024 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 07 Feb 2024 |
| 0.0.0-beta05 | [changelog 🔗](changelog/0.0.0-beta05.md) | 07 Feb 2024 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 07 Feb 2024 |
| 0.0.0 | [changelog 🔗](changelog/0.0.0.md) | 07 Feb 2024 |
| 6.2.4 | [changelog 🔗](changelog/6.2.4.md) | 07 Feb 2024 |

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

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
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
      // Winds Examples Libraries
      implementation libs.winds.examples.demo.tst.2
      implementation libs.winds.examples.kmp.android
      implementation libs.winds.examples.module.1.library.1
      implementation libs.winds.examples.module.1.library.2
      implementation libs.winds.examples.module.1.library.3
      implementation libs.winds.examples.module.1.library.4
    }
    ```
