# Example Data

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Winds description example

API Reference<br>
[`dev.teogor.winds.example:data-*`](../html/data)<br>
[`dev.teogor.winds.example:data-database`](../html/data/database)<br>
[`dev.teogor.winds.example:data-datastore`](../html/data/datastore)<br>

| Latest Update        |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:---------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| February 16, 2024    |        -         |      v2.29.764      |       -        |        -        |
| January 06, 2024     |        -         |          -          |   v3.25.130    |        -        |
| December 06, 2023    |    v1.71.188     |          -          |       -        |        -        |
| November 25, 2023    |    v1.53.302     |          -          |       -        |        -        |
| November 23, 2023    |    v1.81.946     |          -          |       -        |        -        |
| November 05, 2023    |        -         |      v2.50.393      |       -        |        -        |
| September 19, 2023   |        -         |      v2.31.570      |       -        |        -        |
| August 03, 2023      |    v1.68.730     |          -          |       -        |        -        |
| February 04, 2023    |    v1.30.924     |          -          |       -        |        -        |
| February 01, 2023    |        -         |          -          |   v3.30.724    |        -        |

## Declaring dependencies

To use Data in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleData = "1.0.0-alpha02"
        
        implementation "dev.teogor.winds.example:data-database:$teogorExampleData"
        implementation "dev.teogor.winds.example:data-datastore:$teogorExampleData"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleData = "1.0.0-alpha02"
        
        implementation("dev.teogor.winds.example:data-database:$teogorExampleData")
        implementation("dev.teogor.winds.example:data-datastore:$teogorExampleData")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Data documentation](../data/index.md#getting-started-with-data).

## Feedback

Your feedback helps make Data better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/winds/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha02

February 21, 2024

[`dev.teogor.winds.example:data-*:1.0.0-alpha02`](https://github.com/teogor/winds/releases/1.0.0-alpha02) is released. [Version 1.0.0-alpha02 contains these commits](https://github.com/teogor/winds/compare/1.0.0-alpha01...1.0.0-alpha02)

**Bug Fixes**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

**Enhancement**

* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)
* Fixing Illegal `groupId` Modification in Gradle Plugin Subproject ([#63](https://github.com/teogor/winds/issues/63)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

February 19, 2024

[`dev.teogor.winds.example:data-*:1.0.0-alpha01`](https://github.com/teogor/winds/releases/1.0.0-alpha01) is released. [Version 1.0.0-alpha01 contains these commits](https://github.com/teogor/winds/commits/1.0.0-alpha01)

🎊 **Initial Release** 🎊

[//]: # (Do not remove this line or edit the content above it. Automatically generated.)
