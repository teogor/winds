# Querent

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Querent lays the groundwork for your project's resource management, fostering consistency and efficiency across your development workflow.

[//]: # (REGION-API-REFERENCE)

API Reference  
[`dev.teogor.querent:querent-*`](../html/)  
[`dev.teogor.querent:gradle-plugin`](../html/gradle-plugin)  
[`dev.teogor.querent:gradle-plugin-api`](../html/gradle-plugin-api)

[//]: # (REGION-API-REFERENCE)

[//]: # (REGION-RELEASE-TABLE)

| Latest Update    |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:-----------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| March 04, 2024   |        -         |          -          |       -        |  1.0.0-alpha03  |

[//]: # (REGION-RELEASE-TABLE)

[//]: # (REGION-DEPENDENCIES)

## Declaring dependencies

First, add the `querent-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.querent' version '1.0.0-alpha03' apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.querent") version "1.0.0-alpha03" apply false
    }
    ```

Then, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.querent'
    }

    dependencies {
        def teogorQuerent = "1.0.0-alpha03"
        
        
        // optional - Gradle Plugin API support for Querent
        implementation "dev.teogor.querent:gradle-plugin-api:$teogorQuerent"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.querent")
    }

    dependencies {
        val teogorQuerent = "1.0.0-alpha03"
        
        
        // optional - Gradle Plugin API support for Querent
        implementation("dev.teogor.querent:gradle-plugin-api:$teogorQuerent")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Querent documentation](../index.md#getting-started-with-querent).

[//]: # (REGION-DEPENDENCIES)

[//]: # (REGION-FEEDBACK)

## Feedback

Your feedback helps make Querent better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/querent/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/querent/issues/new){ .md-button }

[//]: # (REGION-FEEDBACK)

[//]: # (REGION-VERSION-CHANGELOG)

### Version 2.0.0

#### Version 2.0.0-alpha01

February 23, 2024

[`dev.teogor.querent:querent-*:2.0.0-alpha01`](https://gitlab.com/teogor/querent/releases/2.0.0-alpha01) is released. [Version 2.0.0-alpha01 contains these commits](https://gitlab.com/teogor/querent/compare/1.0.0-alpha02...2.0.0-alpha01)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)

### Version 1.2.0

#### Version 1.2.0

February 19, 2024

[`dev.teogor.querent:querent-*:1.2.0`](https://gitlab.com/teogor/querent/releases/1.2.0) is released. [Version 1.2.0 contains these commits](https://gitlab.com/teogor/querent/compare/1.1.0...1.2.0)

🎊 \*\*Initial Release\*\* 🎊

### Version 1.1.0

#### Version 1.1.0

February 19, 2024

[`dev.teogor.querent:querent-*:1.1.0`](https://gitlab.com/teogor/querent/releases/1.1.0) is released. [Version 1.1.0 contains these commits](https://gitlab.com/teogor/querent/compare/1.0.3...1.1.0)

🎊 \*\*Initial Release\*\* 🎊

### Version 1.0.3

#### Version 1.0.3

February 19, 2024

[`dev.teogor.querent:querent-*:1.0.3`](https://gitlab.com/teogor/querent/releases/1.0.3) is released. [Version 1.0.3 contains these commits](https://gitlab.com/teogor/querent/compare/1.0.2-alpha04...1.0.3)

🎊 \*\*Initial Release\*\* 🎊

### Version 1.0.2

#### Version 1.0.2-alpha04

February 19, 2024

[`dev.teogor.querent:querent-*:1.0.2-alpha04`](https://gitlab.com/teogor/querent/releases/1.0.2-alpha04) is released. [Version 1.0.2-alpha04 contains these commits](https://gitlab.com/teogor/querent/compare/1.0.1-alpha02...1.0.2-alpha04)

🎊 \*\*Initial Release\*\* 🎊

### Version 1.0.1

#### Version 1.0.1-alpha02

February 19, 2024

[`dev.teogor.querent:querent-*:1.0.1-alpha02`](https://gitlab.com/teogor/querent/releases/1.0.1-alpha02) is released. [Version 1.0.1-alpha02 contains these commits](https://gitlab.com/teogor/querent/compare/1.0.0-alpha01...1.0.1-alpha02)

🎊 \*\*Initial Release\*\* 🎊

### Version 1.0.0

#### Version 1.0.0-alpha02

February 21, 2024

[`dev.teogor.querent:querent-*:1.0.0-alpha02`](https://gitlab.com/teogor/querent/releases/1.0.0-alpha02) is released. [Version 1.0.0-alpha02 contains these commits](https://gitlab.com/teogor/querent/compare/1.2.0...1.0.0-alpha02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/querent/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

February 19, 2024

[`dev.teogor.querent:querent-*:1.0.0-alpha01`](https://gitlab.com/teogor/querent/releases/1.0.0-alpha01) is released. [Version 1.0.0-alpha01 contains these commits](https://gitlab.com/teogor/querent/commits/1.0.0-alpha01)

🎊 \*\*Initial Release\*\* 🎊

[//]: # (REGION-VERSION-CHANGELOG)

