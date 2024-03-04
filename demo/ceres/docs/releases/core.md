# Ceres Core

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Querent lays the groundwork for your project's resource management, fostering consistency and efficiency across your development workflow.

[//]: # (REGION-API-REFERENCE)

API Reference  
[`dev.teogor.ceres:core-*`](../html/core)  
[`dev.teogor.ceres:core-common`](../html/core/common)  
[`dev.teogor.ceres:core-design-system`](../html/core/designsystem)  
[`dev.teogor.ceres:core-domain`](../html/core/domain)  
[`dev.teogor.ceres:core-ksp`](../html/core/ksp)  
[`dev.teogor.ceres:core-ktx`](../html/core/ktx)  
[`dev.teogor.ceres:core-network`](../html/core/network)

[//]: # (REGION-API-REFERENCE)

[//]: # (REGION-RELEASE-TABLE)

| Latest Update    |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:-----------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| March 03, 2024   |        -         |          -          |  1.0.0-beta02  |        -        |

[//]: # (REGION-RELEASE-TABLE)

[//]: # (REGION-DEPENDENCIES)

## Declaring dependencies

To use Core in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresCore = "1.0.0-beta02"
        
        implementation "dev.teogor.ceres:core-common:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-design-system:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-domain:$teogorCeresCore"
        
        // To use Kotlin annotation processing tool (kapt)
        kapt "dev.teogor.ceres:core-ksp:$teogorCeresCore"
        // To use Kotlin Symbol Processing (KSP)
        ksp "dev.teogor.ceres:core-ksp:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-ktx:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-network:$teogorCeresCore"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresCore = "1.0.0-beta02"
        
        implementation("dev.teogor.ceres:core-common:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-design-system:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-domain:$teogorCeresCore")
        
        // To use Kotlin annotation processing tool (kapt)
        kapt("dev.teogor.ceres:core-ksp:$teogorCeresCore")
        // To use Kotlin Symbol Processing (KSP)
        ksp("dev.teogor.ceres:core-ksp:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-ktx:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-network:$teogorCeresCore")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Core documentation](../core/index.md#getting-started-with-core).

[//]: # (REGION-DEPENDENCIES)

[//]: # (REGION-FEEDBACK)

## Feedback

Your feedback helps make Core better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/ceres/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/ceres/issues/new){ .md-button }

[//]: # (REGION-FEEDBACK)

[//]: # (REGION-VERSION-CHANGELOG)

### Version 1.0.0

#### Version 1.0.0-beta03

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-beta03`](https://gitlab.com/teogor/ceres/releases/1.0.0-beta03) is released. [Version 1.0.0-beta03 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-beta02...1.0.0-beta03)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-beta02

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-beta02`](https://gitlab.com/teogor/ceres/releases/1.0.0-beta02) is released. [Version 1.0.0-beta02 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-beta01...1.0.0-beta02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-beta01

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-beta01`](https://gitlab.com/teogor/ceres/releases/1.0.0-beta01) is released. [Version 1.0.0-beta01 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-alpha05...1.0.0-beta01)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha05

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-alpha05`](https://gitlab.com/teogor/ceres/releases/1.0.0-alpha05) is released. [Version 1.0.0-alpha05 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-alpha04...1.0.0-alpha05)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha04

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-alpha04`](https://gitlab.com/teogor/ceres/releases/1.0.0-alpha04) is released. [Version 1.0.0-alpha04 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-alpha03...1.0.0-alpha04)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha03

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-alpha03`](https://gitlab.com/teogor/ceres/releases/1.0.0-alpha03) is released. [Version 1.0.0-alpha03 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-alpha02...1.0.0-alpha03)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha02

February 21, 2024

[`dev.teogor.ceres:core-*:1.0.0-alpha02`](https://gitlab.com/teogor/ceres/releases/1.0.0-alpha02) is released. [Version 1.0.0-alpha02 contains these commits](https://gitlab.com/teogor/ceres/compare/1.0.0-alpha01...1.0.0-alpha02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/ceres/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

February 19, 2024

[`dev.teogor.ceres:core-*:1.0.0-alpha01`](https://gitlab.com/teogor/ceres/releases/1.0.0-alpha01) is released. [Version 1.0.0-alpha01 contains these commits](https://gitlab.com/teogor/ceres/commits/1.0.0-alpha01)

🎊 **Initial Release** 🎊

[//]: # (REGION-VERSION-CHANGELOG)

