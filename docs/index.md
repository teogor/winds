## Winds 🍃

### Overview

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Profile](https://source.teogor.dev/badges/teogor-github.svg)](https://github.com/teogor)
[![Open-Source Directory](https://source.teogor.dev/badges/teogor-dev.svg)](https://source.teogor.dev)

Winds aims to provide a simple and efficient way to build and publish libraries and applications for
multiple platforms. It is designed to be flexible and extensible, so that it can be used to build a
wide variety of libraries and applications, from simple utility libraries to complex composable
libraries and applications.

### Features

* Simple and efficient build process
* Flexible and extensible library structure
* Support for multiple publication formats (Maven, AAR, etc.)
* Automatic documentation generation

### How to Apply

**Plugin implementation**

To implement the Winds plugin, add the following plugin ID to your build.gradle file:

=== "Kotlin"

    ```kotlin
    plugins {
      id("dev.teogor.winds") version "1.0.2"
    }
    ```

=== "Groovy"

    ```groovy
    plugins {
      id 'dev.teogor.winds' version '1.0.2'
    }
    ```

To learn more about detailed implementation, please refer to the
comprehensive [documentation](releases.md).

### Usage

```kotlin
winds {
  buildFeatures {
    mavenPublish = true
    docsGenerator = true
  }

  mavenPublish {
    groupId = "my.group.id"
    artifactId = "my-library"
    version = "1.0.0"
  }
}
```

**Generating Documentation**

This project utilizes Gradle tasks to manage documentation generation.

**Running Documentation Generation:**

1. **Winds Markdown Task:**
   To generate documentation using the provided Markdown templates, execute the following command in
   your project's root directory:

   ```shell
   ./gradlew :windsMd
   ```

   This command runs the `windsMd` task, which processes the templates and generates the final
   documentation files.

**Updating Dates without Regeneration:**

In some scenarios, you might want to update timestamps within existing documentation without
regenerating the entire content. To achieve this, use the `forceDateUpdate` parameter with
the `windsMd` task:

   ```shell
   ./gradlew :windsMd -PforceDateUpdate=false
   ```

- **`-PforceDateUpdate=false`:** This parameter instructs the task to skip content regeneration and
  only update timestamps within the existing documentation. Setting it to `true` would normally
  force complete regeneration (not recommended for date updates).

**Generated Documentation Location:**

The generated documentation files will be located in the project's `docs/` directory. You can
access these files to view the project's API reference and other documentation.

## Find this repository useful? 🩷

* Support it by joining __[stargazers](https://github.com/teogor/winds/stargazers)__ for this
  repository. 📁
* Get notified about my new projects by __[following me](https://github.com/teogor)__ on GitHub. 💻
* Interested in sponsoring me? [Support me](sponsor.md) on GitHub! 🤝

# License

```xml
  Designed and developed by 2023 teogor (Teodor Grigor)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
