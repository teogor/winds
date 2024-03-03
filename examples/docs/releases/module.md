# Example Module

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Winds description example

API Reference<br>
[`dev.teogor.winds.example:module-*`](../html/module)<br>
[`dev.teogor.winds.example:module-library-1`](../html/module/library-1)<br>
[`dev.teogor.winds.example:module-library-2`](../html/module/library-2)<br>
[`dev.teogor.winds.example:module-library-4`](../html/module/library-4)<br>
[`dev.teogor.winds.example:module-library-3`](../html/module/library-3)<br>

| Latest Update        |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:---------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| December 28, 2023    |    v1.48.769     |          -          |       -        |        -        |
| November 25, 2023    |        -         |      v2.28.764      |       -        |        -        |
| November 15, 2023    |        -         |          -          |       -        |    v4.78.473    |
| September 06, 2023   |        -         |          -          |       -        |    v4.70.989    |
| August 27, 2023      |    v1.53.885     |          -          |       -        |        -        |
| August 13, 2023      |        -         |          -          |       -        |    v4.81.153    |
| June 19, 2023        |        -         |          -          |   v3.36.653    |        -        |
| May 24, 2023         |        -         |      v2.69.590      |       -        |        -        |
| March 08, 2023       |    v1.20.467     |          -          |       -        |        -        |
| December 14, 2022    |        -         |          -          |   v3.14.814    |        -        |

## Declaring dependencies

To use Module in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleModule = "1.0.0-alpha01"
        
        implementation "dev.teogor.winds.example:module-library-1:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-2:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-4:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-3:$teogorExampleModule"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleModule = "1.0.0-alpha01"
        
        implementation("dev.teogor.winds.example:module-library-1:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-2:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-4:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-3:$teogorExampleModule")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Module documentation](../module/index.md#getting-started-with-module).

## Feedback

Your feedback helps make Module better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/winds/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

[//]: # (Do not remove this line or edit the content above it. Automatically generated.)
