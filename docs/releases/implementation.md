[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha04`](../releases.md)

### Plugin Releases

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha04 | [changelog 🔗](changelog/1.0.0-alpha04.md) | 24 Nov 2023  |
| 1.0.0-alpha03 | [changelog 🔗](changelog/1.0.0-alpha03.md) | 20 Nov 2023  |
| 1.0.0-alpha02 | [changelog 🔗](changelog/1.0.0-alpha02.md) | 08 Nov 2023  |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 03 Nov 2023  |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Winds libraries and individual libraries, in
TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    winds = "1.0.0-alpha04"

    [plugins]
    winds = { id = "dev.teogor.winds", version.ref = "winds" }
    ```

=== "Using Plugin SDK"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    winds = "1.0.0-alpha04"

    [libraries]
    winds-api = { group = "dev.teogor.winds", name = "api", version.ref = "winds" }
    winds-common = { group = "dev.teogor.winds", name = "common", version.ref = "winds" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
      // Winds Libraries
      implementation(libs.winds.api)
      implementation(libs.winds.common)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
      // Winds Libraries
      implementation(libs.winds.api)
      implementation(libs.winds.common)
    }
    ```
