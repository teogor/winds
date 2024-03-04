# Example Stitch

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Winds description example

API Reference<br>
[`dev.teogor.winds.example:stitch-*`](../html/stitch)<br>
[`dev.teogor.winds.example:stitch-common`](../html/stitch/common)<br>
[`dev.teogor.winds.example:stitch-ksp`](../html/stitch/ksp)<br>
[`dev.teogor.winds.example:stitch-plugin`](../html/stitch/plugin)<br>
[`dev.teogor.winds.example:stitch-plugin-api`](../html/stitch/plugin-api)<br>

| Latest Update       |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:--------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| February 20, 2024   |        -         |          -          |   v3.96.134    |        -        |
| February 05, 2024   |        -         |          -          |   v3.26.899    |        -        |
| November 27, 2023   |        -         |      v2.30.339      |       -        |        -        |
| November 12, 2023   |    v1.81.403     |          -          |       -        |        -        |
| November 07, 2023   |    v1.65.611     |          -          |       -        |        -        |
| July 16, 2023       |        -         |          -          |   v3.42.623    |        -        |
| July 12, 2023       |        -         |          -          |       -        |    v4.77.607    |
| June 18, 2023       |        -         |      v2.87.681      |       -        |        -        |
| March 12, 2023      |        -         |          -          |       -        |    v4.11.601    |
| February 18, 2023   |        -         |          -          |   v3.94.360    |        -        |

## Declaring dependencies

First, add the `stitch-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.stitch' version '1.2.1' apply false
        id 'dev.teogor.stitch.args' version '1.2.1' apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.stitch") version "1.2.1" apply false
        id("dev.teogor.stitch.args") version "1.2.1" apply false
    }
    ```

Then, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.stitch'
        id 'dev.teogor.stitch.args'
    }

    dependencies {
        def teogorExampleStitch = "1.2.1"
        
        implementation "dev.teogor.winds.example:stitch-common:$teogorExampleStitch"
        
        // To use Kotlin annotation processing tool (kapt)
        kapt "dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch"
        // To use Kotlin Symbol Processing (KSP)
        ksp "dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch"
        
        // optional - Plugin API support for Stitch
        implementation "dev.teogor.winds.example:stitch-plugin-api:$teogorExampleStitch"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.stitch")
        id("dev.teogor.stitch.args")
    }

    dependencies {
        val teogorExampleStitch = "1.2.1"
        
        implementation("dev.teogor.winds.example:stitch-common:$teogorExampleStitch")
        
        // To use Kotlin annotation processing tool (kapt)
        kapt("dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch")
        // To use Kotlin Symbol Processing (KSP)
        ksp("dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch")
        
        // optional - Plugin API support for Stitch
        implementation("dev.teogor.winds.example:stitch-plugin-api:$teogorExampleStitch")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Stitch documentation](../stitch/index.md#getting-started-with-stitch).

## Feedback

Your feedback helps make Stitch better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/winds/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha02

February 21, 2024

[`dev.teogor.winds.example:stitch-*:1.0.0-alpha02`](https://github.com/teogor/winds/releases/1.0.0-alpha02) is released. [Version 1.0.0-alpha02 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha01...1.0.0-alpha02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

February 19, 2024

[`dev.teogor.winds.example:stitch-*:1.0.0-alpha01`](https://github.com/teogor/winds/releases/1.0.0-alpha01) is released. [Version 1.0.0-alpha01 contains these commits](https://github.com/teogor/winds/commits/1.0.0-alpha01)

🎊 **Initial Release** 🎊

[//]: # (Do not remove this line or edit the content above it. Automatically generated.)
