[//]: # (This file was automatically generated - do not edit)

# Winds

Winds build and publish libraries and applications for multiple platforms, simple and efficient.

---

### API Reference

* [`dev.teogor.winds`](../reference/gradle-plugin)
* [`dev.teogor.winds:api`](../reference/api)
* [`dev.teogor.winds:common`](../reference/common)

### Release

|   Latest Update   | Stable Release | Beta Release | Alpha Release |
|:-----------------:|:--------------:|:------------:|:-------------:|
| November 20, 2023 |       -        |      -       | 1.0.0-alpha03 |

### Declaring dependencies

To add a dependency on Winds, you must add the Maven repository to your project.
Read [Maven's repository for more information](https://repo.maven.apache.org/maven2/).

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

=== "Kotlin"

    ```kotlin
    plugins {
      id("dev.teogor.winds") version "1.0.0-alpha03"
    }
    ```

=== "Groovy"

    ```groovy
    plugins {
      id 'dev.teogor.winds' version '1.0.0-alpha03'
    }
    ```

### Feedback

Your feedback helps make Winds better. We want to know if you discover new issues or have ideas
for improving this library. Before creating a new issue, please take a look at
the [existing ones](https://github.com/teogor/winds) in this library. You can add your vote to an
existing issue by clicking the star button.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha03

November 20, 2023

`dev.teogor.winds:winds-*:1.0.0-alpha03` is
released. [Version 1.0.0-alpha02 contains these commits.](https://github.com/teogor/winds/compare/1.0.0-alpha02...1.0.0-alpha03)

**Enhancement**

* Enhance version handling and introduce new version
  structure ([#26](https://github.com/teogor/winds/pull/26)) by [@teogor](https://github.com/teogor)
* Dependency Management with Flexible Gathering and
  Filtering ([#25](https://github.com/teogor/winds/pull/25)) by [@teogor](https://github.com/teogor)
* Add BoM constraints for subprojects ([#22](https://github.com/teogor/winds/pull/22))
  by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Module documentation for android with improved grouping and
  filtering ([#27](https://github.com/teogor/winds/pull/27)) by [@teogor](https://github.com/teogor)
* Address Missing Developer Information in PoM ([#24](https://github.com/teogor/winds/pull/24))
  by [@teogor](https://github.com/teogor)
* Fix missing license in PoM ([#23](https://github.com/teogor/winds/pull/23))
  by [@teogor](https://github.com/teogor)
* Add BoM constraints for subprojects ([#22](https://github.com/teogor/winds/pull/22))
  by [@teogor](https://github.com/teogor)

**Others**

* Enrich Action Workflow with Accurate Publish
  Commands ([#30](https://github.com/teogor/winds/pull/30)) by [@teogor](https://github.com/teogor)
* Centralize Group and Version for Improved Subproject
  Management ([#29](https://github.com/teogor/winds/pull/29))
  by [@teogor](https://github.com/teogor)
* Fixed wrong api version ([#20](https://github.com/teogor/winds/pull/20))
  by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha02

November 08, 2023

`dev.teogor.winds:winds-*:1.0.0-alpha02` is
released. [Version 1.0.0-alpha02 contains these commits.](https://github.com/teogor/winds/compare/1.0.0-alpha01...1.0.0-alpha02)

**Enhancement**

* Dependency Representation, Alert for Local Project
  Dependencies ([#16](https://github.com/teogor/winds/pull/16))
  by [@teogor](https://github.com/teogor)
* Improved BoM Configuration and Function Naming ([#10](https://github.com/teogor/winds/pull/10))
  by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Enhanced Winds Plugin Support with Project Extension and Module Information
  Retrieval ([#17](https://github.com/teogor/winds/pull/17)) by [@teogor](https://github.com/teogor)
* Handle Resolution of Empty Dependencies in Project
  Configuration ([#15](https://github.com/teogor/winds/pull/15))
  by [@teogor](https://github.com/teogor)
* Resolve Misconfigured Modules Using kotlin-dsl ([#13](https://github.com/teogor/winds/pull/13))
  by [@teogor](https://github.com/teogor)
* Added Javadoc for Developer Interface Properties and Introduced Email
  Field ([#8](https://github.com/teogor/winds/pull/8)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

November 03, 2023

`dev.teogor.winds:winds-*:1.0.0-alpha01` is
released. [Version 1.0.0-alpha01 contains these commits.](https://github.com/teogor/winds/compare/db0013be686149f05638accd534dab2fb81a8818...1.0.0-alpha01)

**Initial Release** 🎊
