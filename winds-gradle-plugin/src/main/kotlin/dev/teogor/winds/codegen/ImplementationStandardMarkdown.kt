/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.winds.codegen

val implementationStandardMarkdownContent = """
[//]: # (This file was automatically generated - do not edit)

## Implementation

### Versions

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
&&versionsTable&&

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of &&projectName&& libraries, including Build of
Materials (BoM) and individual libraries, in TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    &&versionCatalogDefault&&
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
&&dependenciesImplementationKotlin&&
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
&&dependenciesImplementationGroovy&&
    }
    ```
""".trimStart()
