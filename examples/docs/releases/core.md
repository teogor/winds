# Example Core

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Winds description example

API Reference<br>
[`dev.teogor.winds.example:example-core-*`](../html/core)<br>
[`dev.teogor.winds.example:example-core-common`](../html/core/common)<br>
[`dev.teogor.winds.example:example-core-design-system`](../html/core/designsystem)<br>
[`dev.teogor.winds.example:example-core-domain`](../html/core/domain)<br>
[`dev.teogor.winds.example:example-core-ktx`](../html/core/ktx)<br>
[`dev.teogor.winds.example:example-core-network`](../html/core/network)<br>

| Latest Update       |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:--------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| December 27, 2023   |        -         |          -          |   v3.91.728    |        -        |
| August 11, 2023     |        -         |          -          |       -        |    v4.66.748    |
| August 01, 2023     |        -         |      v2.24.807      |       -        |        -        |
| June 03, 2023       |        -         |      v2.72.141      |       -        |        -        |
| April 20, 2023      |        -         |      v2.69.235      |       -        |        -        |
| March 05, 2023      |        -         |      v2.33.474      |       -        |        -        |
| February 02, 2023   |    v1.44.515     |          -          |       -        |        -        |
| January 20, 2023    |    v1.25.539     |          -          |       -        |        -        |
| December 07, 2022   |    v1.83.725     |          -          |       -        |        -        |
| December 04, 2022   |        -         |          -          |   v3.94.684    |        -        |

## Declaring dependencies

To use Core in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleCore = "1.0.0-beta03"
        
        implementation "dev.teogor.winds.example:example-core-common:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-design-system:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-domain:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-ktx:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-network:$teogorExampleCore"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleCore = "1.0.0-beta03"
        
        implementation("dev.teogor.winds.example:example-core-common:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-design-system:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-domain:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-ktx:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-network:$teogorExampleCore")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Core documentation](../core/index.md#getting-started-with-core).

## Feedback

Your feedback helps make Core better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/winds/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-beta03

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-beta03`](https://github.com/teogor/winds/releases/1.0.0-beta03) is released. [Version 1.0.0-beta03 contains these commits](https://github.com/teogor/winds/compare/1.0.0-beta02...1.0.0-beta03)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-beta02

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-beta02`](https://github.com/teogor/winds/releases/1.0.0-beta02) is released. [Version 1.0.0-beta02 contains these commits](https://github.com/teogor/winds/compare/1.0.0-beta01...1.0.0-beta02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-beta01

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-beta01`](https://github.com/teogor/winds/releases/1.0.0-beta01) is released. [Version 1.0.0-beta01 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha05...1.0.0-beta01)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha05

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-alpha05`](https://github.com/teogor/winds/releases/1.0.0-alpha05) is released. [Version 1.0.0-alpha05 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha04...1.0.0-alpha05)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha04

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-alpha04`](https://github.com/teogor/winds/releases/1.0.0-alpha04) is released. [Version 1.0.0-alpha04 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha03...1.0.0-alpha04)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha03

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-alpha03`](https://github.com/teogor/winds/releases/1.0.0-alpha03) is released. [Version 1.0.0-alpha03 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha02...1.0.0-alpha03)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha02

February 21, 2024

[`dev.teogor.winds.example:core-*:1.0.0-alpha02`](https://github.com/teogor/winds/releases/1.0.0-alpha02) is released. [Version 1.0.0-alpha02 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha01...1.0.0-alpha02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

February 19, 2024

[`dev.teogor.winds.example:core-*:1.0.0-alpha01`](https://github.com/teogor/winds/releases/1.0.0-alpha01) is released. [Version 1.0.0-alpha01 contains these commits](https://github.com/teogor/winds/commits/1.0.0-alpha01)

🎊 **Initial Release** 🎊

[//]: # (Do not remove this line or edit the content above it. Automatically generated.)
