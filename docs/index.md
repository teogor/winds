## Xenoglot 🌍

### Overview

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/dev.teogor.xenoglot/bom.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=g%3Adev.teogor.xenoglot+a%3Abom&smo=true)
[![Profile](https://source.teogor.dev/badges/teogor-github.svg)](https://github.com/teogor)
[![Open-Source Directory](https://source.teogor.dev/badges/teogor-dev.svg)](https://source.teogor.dev)

**Xenoglot: A Comprehensive Language Data Management Toolkit**

Xenoglot is a comprehensive library for representing, managing, and interacting with language data.
It provides a rich set of classes and interfaces for representing languages, dialects, scripts,
language families, and regions. The library also includes methods for obtaining language tags,
checking language usage patterns, and territorializing languages. Xenoglot is essential for any
project that requires accurate and consistent handling of language data.

**Key Features**

* **Comprehensive Language Representation:** Xenoglot provides a structured representation of
  languages, dialects, scripts, language families, and regions, enabling accurate and consistent
  handling of language data.

* **Language Tag Generation:** Xenoglot provides methods for generating language tags, facilitating
  interoperability with language-related protocols and standards.

* **Language Usage Analysis:** Xenoglot includes methods for checking language usage patterns,
  enabling informed decisions about language selection and content localization.

* **Language Territorialization:** Xenoglot provides functionality for territorializing languages,
  adapting them to specific geographical or political contexts.

### Installation

For a detailed installation guide, please refer to the following page: [releases](releases.md).

**Usage**

The following examples demonstrate how to use Xenoglot to represent and interact with language data:

**Retrieving Language Information**

```kotlin
val language = Language.English
val languageTag = language.languageTag
val languageFamily = language.languageFamily
val isSpokenInRomania = language.isSpokenIn(Country.Romania)
```

**Checking Language Usage Patterns**

```kotlin
val isDialect = Dialect("en-US", Language.English).isDialect
val isScriptUsedInLanguage = Script.Cyrillic.isWrittenIn(Language.Russian)
```

**Interacting with Language Tags**

```kotlin
val languageTag = LanguageTag("fr", LanguageFamily.Romance)
val language = languageTag.asLanguage()
val countryCode = languageTag.substringAfter("-")
```

**Territorializing Languages**

```kotlin
val dialect = Language.English.territorialize(Country.Australia)
val dialectLanguageTag = dialect.languageTag
val dialectCountryCode = dialect.country.code
```

**Benefits of Using Xenoglot**

* **Improved Language Handling:** Xenoglot provides a consistent and structured approach to managing
  language data, reducing errors and inconsistencies.

* **Enhanced Interoperability:** Xenoglot facilitates interoperability with language-related
  protocols and standards, enabling seamless integration with external systems.

* **Simplified Language-Related Tasks:** Xenoglot streamlines language-related tasks, such as
  localization, translation, and language detection.

* **Promote Language-Aware Development:** Xenoglot encourages language-aware development practices,
  ensuring that language data is handled accurately and appropriately.

**Getting Started**

To get started with Xenoglot, refer to the comprehensive documentation available at
[source.teogor.dev/xenoglot](https://source.teogor.dev/xenoglot). The documentation provides
detailed explanations, examples, and best practices for using the library effectively.

## Find this repository useful? 🩷

* Support it by joining __[stargazers](https://github.com/teogor/xenoglot/stargazers)__ for this
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
