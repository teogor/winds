[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.1`](../releases.md)

### Plugin Releases

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
|     1.0.1     |     [changelog 🔗](changelog/1.0.1.md)     | 18 Apr 2024  |
|     1.0.0     |     [changelog 🔗](changelog/1.0.0.md)     | 15 Apr 2024  |
| 1.0.0-beta04  | [changelog 🔗](changelog/1.0.0-beta04.md)  | 04 Mar 2024  |
| 1.0.0-beta03  | [changelog 🔗](changelog/1.0.0-beta03.md)  | 27 Feb 2024  |
| 1.0.0-beta02  | [changelog 🔗](changelog/1.0.0-beta02.md)  | 19 Feb 2024  |
| 1.0.0-beta01  | [changelog 🔗](changelog/1.0.0-beta01.md)  | 08 Feb 2024  |
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
    teogor-winds = "1.0.1"

    [plugins]
    teogor-winds = { id = "dev.teogor.winds", version.ref = "teogor-winds" }
    ```

=== "Using Plugin SDK"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-winds = "1.0.1"

    [libraries]
    teogor-winds-api = { module = "dev.teogor.winds:api", version.ref = "teogor-winds" }
    teogor-winds-common = { module = "dev.teogor.winds:common", version.ref = "teogor-winds" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
      // Winds Plugin
      alias(libs.plugins.teogor.winds)
    }

    dependencies {
      // Winds Libraries
      implementation(libs.teogor.winds.api)
      implementation(libs.teogor.winds.common)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
      // Winds Plugin
      alias(libs.plugins.teogor.winds)
    }

    dependencies {
      // Winds Libraries
      implementation(libs.teogor.winds.api)
      implementation(libs.teogor.winds.common)
    }
    ```
