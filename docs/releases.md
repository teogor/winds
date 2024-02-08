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
| February 08, 2023 |       -        |      -       | 1.0.0-beta01  |

### Declaring dependencies

To add a dependency on Winds, you must add the Maven repository to your project.
Read [Maven's repository for more information](https://repo.maven.apache.org/maven2/).

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

=== "Kotlin"

    ```kotlin
    plugins {
      id("dev.teogor.winds") version "1.0.0-beta01"
    }
    ```

=== "Groovy"

    ```groovy
    plugins {
      id 'dev.teogor.winds' version '1.0.0-beta01'
    }
    ```

### Feedback

Your feedback helps make Winds better. We want to know if you discover new issues or have ideas
for improving this library. Before creating a new issue, please take a look at
the [existing ones](https://github.com/teogor/winds) in this library. You can add your vote to an
existing issue by clicking the star button.

[Create a new issue](https://github.com/teogor/winds/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-beta01

February 08, 2023

`dev.teogor.winds:winds-*:1.0.0-beta01` is
released. [Version 1.0.0-beta01 contains these commits.](https://github.com/teogor/winds/compare/1.0.0-alpha04...1.0.0-beta01)

**Enhancement**

* Streamline Doc Generation with Flexible Dependency Handling and MkDocs
  Integration ([#48](https://github.com/teogor/winds/pull/48))
  by [@teogor](https://github.com/teogor)
* Consolidate Winds Maven Publish configuration across child
  projects ([#46](https://github.com/teogor/winds/pull/46)) by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Streamline Doc Generation with Flexible Dependency Handling and MkDocs
  Integration ([#48](https://github.com/teogor/winds/pull/48))
  by [@teogor](https://github.com/teogor)
* Ensure null check before depending on parent publish
  task ([#47](https://github.com/teogor/winds/pull/47))
  by [@teogor](https://github.com/teogor)
* Improve DocsGeneratorTask with project directory & BOM library
  formatting ([#45](https://github.com/teogor/winds/pull/45))
  by [@teogor](https://github.com/teogor)
* Unify BOM constraints with mavenPublish
  configuration ([#44](https://github.com/teogor/winds/pull/44))
  by [@teogor](https://github.com/teogor)
* Ensure consistent artifact ID format ([#43](https://github.com/teogor/winds/pull/43))
  by [@teogor](https://github.com/teogor)
* Fix infinite loop in MavenPublish `displayName`
  getter ([#41](https://github.com/teogor/winds/pull/41)) by [@teogor](https://github.com/teogor)

**Documentation**

* Enhance Naming Consistency in API Reference
  Documentation ([#42](https://github.com/teogor/winds/pull/42))
  by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha04

November 24, 2023

`dev.teogor.winds:winds-*:1.0.0-alpha04` is
released. [Version 1.0.0-alpha04 contains these commits.](https://github.com/teogor/winds/compare/1.0.0-alpha03...1.0.0-alpha04)

**Enhancement**

* Introduce ProjectPluginUtils for Managing Project
  Plugins ([#34](https://github.com/teogor/winds/pull/34)) by [@teogor](https://github.com/teogor)
* Refined Docs Generator Task for Streamlined
  Documentation ([#35](https://github.com/teogor/winds/pull/35))
  by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Fixed the Flow of BoM Dependency Collection ([#37](https://github.com/teogor/winds/pull/37))
  by [@teogor](https://github.com/teogor)
* Introduce processWindsChildProjects for Streamlined Child Project
  Processing ([#36](https://github.com/teogor/winds/pull/36))
  by [@teogor](https://github.com/teogor)
* Correct file and directory creation logic in
  BaseGeneratorTask ([#33](https://github.com/teogor/winds/pull/33))
  by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha03

November 20, 2023

`dev.teogor.winds:winds-*:1.0.0-alpha03` is
released. [Version 1.0.0-alpha03 contains these commits.](https://github.com/teogor/winds/compare/1.0.0-alpha02...1.0.0-alpha03)

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
